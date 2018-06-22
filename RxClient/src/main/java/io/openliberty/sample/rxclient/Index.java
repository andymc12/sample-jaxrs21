package io.openliberty.sample.rxclient;

import javax.servlet.ServletContext;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@ApplicationPath("/")
@Produces(MediaType.TEXT_HTML)
@Path("/index.html")
public class Index extends Application {

    @GET
    public Response index(@Context ServletContext servletContext) {
        
        return Response.ok().entity(servletContext.getResourceAsStream("index.html")).build();
    }
}
