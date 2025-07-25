package com.diesgut.domain.auth;

import io.smallrye.mutiny.Uni;

public interface AuthService {
    Uni<String> authenticate(AuthDto authRequest);
}
