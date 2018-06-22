package io.openliberty.sample.rxclient;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rx")
@Produces("text/plain")
public class ExampleResource {

    @GET
    public Response bookBestAvailableConsultantWithSkill(@QueryParam("skill") String skill,
                                                         @QueryParam("start") String start,
                                                         @QueryParam("end") String end,
                                                         @QueryParam("customer") String customer) {

        StringBuilder sb = new StringBuilder();

        Client c = ClientBuilder.newClient()
                                .register(ExpertListMessageBodyReader.class);

        WebTarget target = c.target("http://localhost:9080/Skills/skills/experts/");
        CompletionStage<List<Expert>> cs = target.path("skill/{skill}")
                                                .resolveTemplate("skill", skill)
                                                .request()
                                                .rx()
                                                .get(new GenericType<List<Expert>>() {});

        // At this point, we have a set of all experts with the necessary skill.
        // Now let's check their schedule.
        try {
            Expert bookedExpert = 
                cs.thenCombine(c.target("http://localhost:9080/Scheduling/schedule/available")
                                .queryParam("start", start)
                                .queryParam("end", end)
                                .request()
                                .rx()
                                .get(new GenericType<Set<String>>() {}),
                    (experts, availableExperts) -> {

                        sb.append("Experts in ").append(skill).append(": ").append(experts).append("\n\n");
                        sb.append("Experts available from ").append(start).append(" to ").append(end).append(": ").append(availableExperts).append("\n\n");
                        return experts.stream()
                                  .filter(e -> availableExperts.contains(e.getName()))
                                  .collect(Collectors.toList());

                    })
        // now we have a sorted list of experts with the skill who are available - time to book
                  .thenApply(experts -> {
                      for (Expert e : experts) {
                          Booking b = new Booking().forConsultant(e.getName())
                                  .withCustomer(customer)
                                  .starting(LocalDate.parse(start))
                                  .ending(LocalDate.parse(end));
                          try {
                              c.target("http://localhost:9080/Scheduling/schedule/bookings")
                               .request(MediaType.APPLICATION_JSON_TYPE)
                               .post(Entity.json(b));

                              return e;
                          } catch (WebApplicationException ex) {
                              if (ex.getResponse().getStatus() == 409) {
                                  System.out.println(e.getName() + " is already booked...");
                              } else {
                                  ex.printStackTrace();
                              }
                          }
                      }
                      return null;
              }).toCompletableFuture().get();

            if (bookedExpert != null) {
                sb.append("Booked: ").append(bookedExpert.getName());
                System.out.println(sb.toString());
            } else {
                sb.append("No expert is available at that time...");
                System.out.println(sb.toString());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return Response.ok(sb.toString()).build();
    }
}
