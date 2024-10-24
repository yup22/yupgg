package com.yupGG.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration

@EnableWebSecurity //웹보안 가능하게 한다.
public class SecurityConfig {
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/img/**", "/favicon.ico", "/error","/payment","/comment").permitAll()
                .requestMatchers("/", "/members/**","/cart/**", "/item/**", "/images/**", "/category/**","/infinity/**","/riot/**","/board/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()

        ).formLogin(formLogin -> formLogin
                .loginPage("/members/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login?error=true")

        ).logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
        ).oauth2Login(oauthLogin -> oauthLogin
                .defaultSuccessUrl("/")
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService))
                .failureUrl("/members/login?error1=true")
        );

        http.exceptionHandling(exception -> exception
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint()));

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


}
