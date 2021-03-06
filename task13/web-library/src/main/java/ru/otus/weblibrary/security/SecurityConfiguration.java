package ru.otus.weblibrary.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
/*
    @Override
    public void configure(WebSecurity web) {
      //  web.ignoring().antMatchers( "/" );
    }
*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().antMatchers("/").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/book*").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/authorlist").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/genrelist").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/remark*").hasAnyRole("ADMIN", "USER")
                .and()
                .formLogin()
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .usernameParameter("j_username")
                .passwordParameter("j_password")

        ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Autowired
    public void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
