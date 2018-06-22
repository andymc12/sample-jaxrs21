package io.openliberty.sample.ssechat;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@ApplicationPath("rest")
@Path("chat")
@ApplicationScoped
public class ChatResource extends Application {

    private AtomicReference<SseBroadcaster> broadcaster = new AtomicReference<>();

    @Context
    protected Sse sse;

    @Override
    public Set<Object> getSingletons() {
        return Collections.singleton(this);
    }

    @GET
    @Path("register")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void register(@Context SseEventSink sink) {
        if (broadcaster.get() == null) {
            broadcaster.compareAndSet(null, sse.newBroadcaster());
        }
        broadcaster.get().register(sink);
    }

    @PUT
    public void broadcast(@QueryParam("user") String user, @QueryParam("message") String message) {
        if (broadcaster.get() == null) {
            System.out.println("Nobody is currently listening - no-op");
            return;
        }
        ChatMessage chatMessage = new ChatMessage(user, message);
        OutboundSseEvent event = sse.newEventBuilder().data(ChatMessage.class, chatMessage)
                .id(""+chatMessage.getMsgID()).mediaType(MediaType.APPLICATION_JSON_TYPE).build();
        broadcaster.get().broadcast(event);
    }
}
