package cloud.forum.config;

import cloud.forum.repository.ForumUserRepository;
import cloud.forum.service.ForumUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//Configuration of The SpringSecurity module, enabling us authentication and authorization features
//@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successForwardUrl("/forum/all")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .cors().disable();
    }

    @Bean
    public UserDetailsService userDetailsService(ForumUserRepository userRepository) {
        return new ForumUserDetailsService(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
