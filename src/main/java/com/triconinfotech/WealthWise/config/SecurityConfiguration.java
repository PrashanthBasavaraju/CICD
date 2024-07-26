package com.triconinfotech.WealthWise.config;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.triconinfotech.WealthWise.security.DecodeJwt;
import com.triconinfotech.WealthWise.security.JWTAuthorizationFilter;

/**
 * The SecurityConfiguration class configures security settings for the application.
 */
@Configuration
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private static final String[] AUTH_WHITE_LIST = {
            "/v2/api-docs",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    };

    @Value("${secret.key}")
    private String SECRET_KEY;
    private final DecodeJwt decodeJwt;

    /**
     * Configures the security filter chain for the application.
     *
     * @param http The HttpSecurity object to configure security settings.
     * @return The configured SecurityFilterChain.
     * @throws Exception If an exception occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new JWTAuthorizationFilter(SECRET_KEY, decodeJwt), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(AUTH_WHITE_LIST).permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(AUTH_WHITE_LIST);
    }
    @Bean
    UserDetailsService emptyDetailsService() {
        return username -> { throw new UsernameNotFoundException("no local users, only JWT tokens allowed"); };
    }
}

