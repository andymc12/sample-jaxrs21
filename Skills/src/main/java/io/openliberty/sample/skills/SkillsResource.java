package io.openliberty.sample.skills;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Path("/skills")
public class SkillsResource extends Application {

    static Set<String> skills = new HashSet<>();
    static Set<Expert> experts = new HashSet<>();

    @GET
    public Set<String> allSkills() {
        return skills;
    }

    @POST
    public void newSkill(String skill) {
        if (!skills.add(skill)) {
            throw new WebApplicationException("Skill " + skill + " already exists", 409);
        }
    }

    @GET
    @Path("/experts")
    public Set<Expert> allExperts() {
        return experts;
    }

    @GET
    @Path("/experts/skill/{skill}")
    public List<Expert> allExpertsWithSkill(@PathParam("skill") String skill) {
        return experts.stream()
                      .filter(expert -> expert.getSkills().containsKey(skill))
                      .sorted( 
                          (Expert e1, Expert e2) -> e2.getSkills().get(skill).compareTo(e1.getSkills().get(skill)))
                      .collect(Collectors.toList());
    }

    @GET
    @Path("/experts/{name}")
    public Expert getExpert(@PathParam("name") String name) {
        for (Expert expert: experts) {
            if (expert.getName().equals(name)) {
                return expert;
            }
        }
        throw new NotFoundException("No expert named \"" + name + "\" was found.");
    }

    @DELETE
    @Path("/experts/{name}")
    public void removeExpert(@PathParam("name") String name) {
        Expert toRemove = null;
        for (Expert expert: experts) {
            if (expert.getName().equals(name)) {
                toRemove = expert;
                break;
            }
        }
        if (toRemove == null) {
            throw new NotFoundException("No expert named \"" + name + "\" was found.");
        }
        experts.remove(toRemove);
    }

    @POST
    @Path("/experts")
    public void newExpert(String expertName) {
        if (!experts.add(new Expert(expertName))) {
            throw new WebApplicationException("Expert " + expertName + " already exists", 409);
        }
    }

    @PUT
    @Path("/experts/{name}")
    public void updateExpert(@PathParam("name") String name, Expert updatedExpert) {
        removeExpert(name);
        experts.add(updatedExpert);
    }
}
