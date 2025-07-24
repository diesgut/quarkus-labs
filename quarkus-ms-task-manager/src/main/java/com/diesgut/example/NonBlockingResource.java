package com.diesgut.example;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("unblocking")
public class NonBlockingResource {
    @GET
    public Uni<String> index() {
        return Uni.createFrom().item("I AM A").onItem().
                transform(s -> s + " UNBLOCKING RESOURCE");
    }
}
