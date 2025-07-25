package com.diesgut.domain.user;


import com.diesgut.common.ApiConstants;
import com.diesgut.domain.user.dto.CreateUserDto;
import com.diesgut.domain.user.dto.UpdateUserDto;
import com.diesgut.domain.user.dto.UserDto;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Path(ApiConstants.API_V1_VERSION + "/users")
public class UserResource {
    private final UserService service;

    @GET
    public Uni<List<UserDto>> get() {
        return service.list();
    }

    @GET
    @Path("{id}")
    public Uni<UserDto> get(@PathParam("id") long id) {
        return service.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<UserDto> create(CreateUserDto user) {
        return service.create(user);
    }


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Uni<UserDto> update(@PathParam("id") Long id, UpdateUserDto user) {
        user.setId(id);
        return service.update(user);
    }

    @DELETE
    @Path("{id}")
    public Uni<Void> delete(@PathParam("id") long id) {
        return service.delete(id);
    }

    @GET
    @Path("self")
    public Uni<UserDto> getCurrentUser() {
        return service.getCurrentUserDto();
    }
}
