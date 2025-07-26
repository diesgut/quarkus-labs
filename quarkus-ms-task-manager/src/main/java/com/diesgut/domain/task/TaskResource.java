package com.diesgut.domain.task;

import com.diesgut.common.ApiConstants;
import com.diesgut.domain.task.dto.CreateTaskDto;
import com.diesgut.domain.task.dto.TaskDto;
import com.diesgut.domain.task.dto.UpdateTaskDto;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Path(ApiConstants.API_V1_VERSION + "/tasks")
public class TaskResource {
    private final TaskService service;

    @GET
    public Uni<List<TaskDto>> all() {
        return service.listForUser();
    }

    @GET
    @Path("{id}")
    public Uni<TaskDto> find(@PathParam("id") Long id) {
        return service.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<TaskDto> create(CreateTaskDto task) {
        return service.create(task);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Uni<TaskDto> update(@PathParam("id") Long id, UpdateTaskDto task) {
        task.id = id;
        return service.update(task);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@PathParam("id") Long id) {
        return service.delete(id);
    }

    @PUT
    @Path("/{id}/complete")
    public Uni<Boolean> setComplete(@PathParam("id") Long id, boolean complete) {
        return service.setComplete(id, complete);
    }
}
