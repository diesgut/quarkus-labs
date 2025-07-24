package com.diesgut.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("blocking")
public class BlockingResource {
    @GET
    public String index() {
        return "I AM A BLOCKING RESOURCE";
    }
}
