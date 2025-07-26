package com.diesgut.domain.auth;

import com.diesgut.common.ApiConstants;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@Path(ApiConstants.API_V1_VERSION + "/auth")
public class AuthResource {
    private final AuthService service;

    @PermitAll
    @POST
    @Path("/login")
    public Uni<String> login(AuthDto authDto) {
        return service.authenticate(authDto);
    }
}
