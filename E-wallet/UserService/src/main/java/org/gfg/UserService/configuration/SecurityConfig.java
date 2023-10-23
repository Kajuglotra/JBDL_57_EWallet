package org.gfg.UserService.configuration;

import org.gfg.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Value("${admin.authority}")
    private String adminAuthority;
    @Value("${user.authority}")
    private String userAuthority;

    @Value("${service.authority}")
    private String serviceAuthority;

    //authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
    //authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().httpBasic().and().authorizeRequests().
                antMatchers(HttpMethod.POST, "/user/**").permitAll().
                antMatchers("/user/userDetails").hasAuthority(userAuthority).
                antMatchers("/user/getUser/**").hasAnyAuthority(adminAuthority, serviceAuthority).
                antMatchers("/**").permitAll().and().formLogin();
    }
}