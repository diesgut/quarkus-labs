package com.diesgut.domain.project;

import com.diesgut.common.ApiConstants;
import com.diesgut.domain.project.dto.CreateProjectDto;
import com.diesgut.domain.project.dto.ProjectDto;
import com.diesgut.domain.project.dto.UpdateProjectDto;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Path(ApiConstants.API_V1_VERSION + "/projects")
public class ProjectResource {
    private final ProjectService service;

    @GET
    public Uni<List<ProjectDto>> all() {
        return service.listForUser();
    }

    @GET
    @Path("{id}")
    public Uni<ProjectDto> find(@PathParam("id") long id) {
        return service.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Uni<ProjectDto> create(CreateProjectDto project) {
        return service.create(project);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Uni<ProjectDto> update(@PathParam("id") Long id, UpdateProjectDto project) {
        project.id = id;
        return service.update(project);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> delete(@PathParam("id") Long id) {
        return service.delete(id);
    }
}
