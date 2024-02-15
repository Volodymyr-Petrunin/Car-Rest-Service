package org.car.rest.security;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.*;


@Configuration
@EnableWebSecurity
public class OAuth2ResourceServerSecurityConfiguration {

    @Value("${principal.claim.name}")
    private String principalClaimName;

    @Value("${mapper.claim.name}")
    private String mapperClaimName;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET).hasRole(Role.USER.toString())
                        .requestMatchers(HttpMethod.POST).hasRole(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.PUT).hasRole(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.PATCH).hasRole(Role.ADMIN.toString())
                        .requestMatchers(HttpMethod.DELETE).hasRole(Role.ADMIN.toString())
                )
                .oauth2ResourceServer(resource -> resource.jwt(withDefaults()))
                .build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        var converter = new JwtAuthenticationConverter();
        converter.setPrincipalClaimName(principalClaimName);

        var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
           var authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            var roles = jwt.getClaimAsStringList(mapperClaimName);

            return Stream.concat(authorities.stream(), roles.stream().filter(role -> role.startsWith("ROLE_")))
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .map(GrantedAuthority.class::cast).toList();
        });

        return converter;
    }
}
