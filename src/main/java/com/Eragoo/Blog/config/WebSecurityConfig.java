package com.Eragoo.Blog.config;

import com.Eragoo.Blog.security.JwtAuthenticationFilter;
import com.Eragoo.Blog.security.JwtConfigurer;
import com.Eragoo.Blog.security.JwtTokenProvider;
import com.Eragoo.Blog.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtTokenProvider tokenProvider;
    private JwtConfigurer jwtConfigurer;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                .antMatchers("/swagger-ui.html")
                .antMatchers("/webjars/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v2/api-docs")
                .antMatchers("/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth//username-password-token").permitAll()
                .anyRequest().authenticated()
                .and()
                //.addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenProvider))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(jwtConfigurer);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
