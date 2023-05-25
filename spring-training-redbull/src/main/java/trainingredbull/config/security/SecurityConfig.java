package trainingredbull.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import lombok.AllArgsConstructor;
import trainingredbull.entity.RoleType;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable().cors().disable().authorizeHttpRequests(auth -> {
            // admin
            auth.requestMatchers("/admin/**").hasAuthority(RoleType.ROLE_ADMIN.toString());

            // user
            auth.requestMatchers("/user/**").hasAnyAuthority(RoleType.ROLE_USER.toString());

            // public
            auth.anyRequest().permitAll();
        });

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
