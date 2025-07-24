package com.diesgut.domain.user;


import com.diesgut.common.ApiConstants;
import com.diesgut.domain.user.dto.UserDto;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Path(ApiConstants.API_V1_VERSION + "/users")
public class UserResource {
    private final UserService service;

    @GET
    public Uni<List<UserDto>> list() {
        return service.list();
    }
}
