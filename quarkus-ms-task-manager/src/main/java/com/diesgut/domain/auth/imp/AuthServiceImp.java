package com.diesgut.domain.auth.imp;

import com.diesgut.domain.auth.AuthDto;
import com.diesgut.domain.auth.AuthService;
import com.diesgut.domain.user.UserEntity;
import com.diesgut.domain.user.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.AuthenticationFailedException;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Duration;
import java.util.HashSet;

@RequiredArgsConstructor(onConstructor_ = @Inject)
@ApplicationScoped
public class AuthServiceImp implements AuthService {

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private String issuer;


    @Override
    public Uni<String> authenticate(AuthDto authRequest) {
        return UserEntity.<UserEntity>find("name", authRequest.name())
                .firstResult()
                .onItem()
                .transform(user -> {
                    if (user == null || !BcryptUtil.matches(authRequest.password(), user.password)) {
                        throw new AuthenticationFailedException("Invalid credentials");
                    }
                    return Jwt.issuer(issuer)
                            .upn(user.name)
                            .groups(new HashSet<>(user.roles))
                            .expiresIn(Duration.ofHours(1L))
                            .sign();
                });
    }
}
