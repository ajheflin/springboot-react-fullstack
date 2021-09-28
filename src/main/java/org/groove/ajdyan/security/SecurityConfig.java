package org.groove.ajdyan.security;

import org.groove.ajdyan.filters.JwtRequestFilter;
import org.groove.ajdyan.entity.user.details.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //Autowire the user details service from JPAUserDetailsService
    @Autowired
    private JPAUserDetailsService userDetailsService;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    //Configure the authbuilder to use a userdetailsservice as the datasource
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    //Set up authorization for certain endpoints
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/user").hasAnyRole("ADMIN", "USER")
//                .antMatchers("/login").permitAll()
//                .antMatchers("/").permitAll()
//                .antMatchers("/check").permitAll()
//                .and().formLogin()
//                .loginPage("/login").permitAll()
//                .loginProcessingUrl("/authenticate")
//                .defaultSuccessUrl("/user")
//                .failureUrl("/login?error")
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .and()
//                .cors().and().csrf().disable();
        http.csrf().disable()
                .cors().disable()
                .authorizeRequests().antMatchers("/authenticate").permitAll()
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/promote/admin").hasRole("ADMIN")
                .antMatchers("/api/users").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //Create new PasswordEncoder for use for account creation/login
    @Bean
    public PasswordEncoder getPasswordEncoder() { return new BCryptPasswordEncoder(); }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
