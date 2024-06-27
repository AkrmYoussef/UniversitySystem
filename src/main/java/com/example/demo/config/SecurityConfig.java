package com.example.demo.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.example.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.http.HttpMethod;

import com.example.demo.service.AuthEntryPoint;
import com.example.demo.service.AuthenticationFilter;
import java.util.Arrays;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  private final UserService userService;
  private final AuthenticationFilter authenticationFilter;
  private final AuthEntryPoint exceptionHandler;

  public SecurityConfig(@Lazy UserService userService, AuthenticationFilter authenticationFilter,
      AuthEntryPoint exceptionHandler) {
    this.userService = userService;
    this.authenticationFilter = authenticationFilter;
    this.exceptionHandler = exceptionHandler;
  }

  public void configureGlobal(AuthenticationManagerBuilder auth)
      throws Exception {
    auth.userDetailsService(userService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager AuthenticationManager(
      AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable()).cors(withDefaults())
        .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.anyRequest().permitAll());
    // http.csrf((csrf) -> csrf.disable())
    //     .cors(withDefaults())
    //     .sessionManagement(
    //         (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    //     .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // Allow frames from the
    //                                                                                          // same origin
    //     .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
    //         .requestMatchers(HttpMethod.POST, "/api/login")
    //         .permitAll()
    //         .requestMatchers(HttpMethod.POST, "/api/users/createUser").permitAll()
    //         .requestMatchers(HttpMethod.GET, "/api/instructors/getLoggedInInstructorCourses").hasAnyRole("ADMIN",
    // "INSTRUCTOR")
    //         .requestMatchers("/api/instructors/**").hasRole("ADMIN")
    //         .requestMatchers(HttpMethod.DELETE,"/api/courses/deleteCourse/**").hasRole("ADMIN")
    //         .requestMatchers(HttpMethod.POST,"/api/courses/createCourse").hasRole("ADMIN")
    //         .requestMatchers("/api/admins/**").hasRole("ADMIN")
    //         .requestMatchers("/h2-console/**").permitAll()
    //         .anyRequest().authenticated())
    //     .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
    //     .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(exceptionHandler));

    // System.out.println("Security filter chain configured");
     return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("*"));
    config.setAllowedHeaders(Arrays.asList("*"));
    config.setAllowCredentials(false);
    config.applyPermitDefaultValues();
    source.registerCorsConfiguration("/**", config);
    return source;

  }

}