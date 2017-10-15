package com.restdatabus.config;

import com.restdatabus.security.AccountAuthenticationProvider;
import com.restdatabus.security.CustomAuthenticationSuccessHandler;
import com.restdatabus.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Setup a session id based HTTP authentication.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Pbkdf2Config pbkdf2Config = new Pbkdf2Config("", 512, 64000);


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(pbkdf2Config.secret, pbkdf2Config.iteration, pbkdf2Config.hashWidth);
    }

    @Bean
    public static AuthenticationSuccessHandler successHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public static LogoutSuccessHandler logoutHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Autowired
    private AccountAuthenticationProvider accountAuthenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/api/**")
                        .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                    .antMatchers("/admin/**")
                        .access("hasRole('ROLE_ADMIN')")
                    .and()
                .formLogin()
                    .loginProcessingUrl("/login")
                    .successHandler(successHandler())
                    .permitAll()
                    .and()
                .csrf()
                    .disable()
                .cors()
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .invalidateHttpSession(true)
                    .deleteCookies(GlobalConfig.JSESSIONID)
                    .clearAuthentication(true)
                .logoutSuccessHandler(logoutHandler())
                    .permitAll();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("*"));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        configuration.setAllowCredentials(true);configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(accountAuthenticationProvider);
    }

    private static class Pbkdf2Config {

        private final String secret;
        private final int hashWidth;
        private final int iteration;

        public Pbkdf2Config(String secret, int hashWidth, int iteration) {
            this.secret = secret;
            this.hashWidth = hashWidth;
            this.iteration = iteration;
        }
    }

}
