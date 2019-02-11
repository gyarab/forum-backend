package cloud.forum.config;

import com.naturalprogrammer.spring.lemon.security.LemonJpaSecurityConfig;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

//Configuration of The SpringSecurity module, enabling us authentication and authorization features
@Component
public class SecurityConfig extends LemonJpaSecurityConfig {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .successForwardUrl("/forum/all")
//                .and()
//                .httpBasic()
//                .and()
//                .csrf().disable()
//                .cors().disable();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService(ForumUserRepository userRepository) {
//        return new ForumUserDetailsService(userRepository);
//    }

    @Override
    protected void authorizeRequests(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/forum/**").authenticated();
        super.authorizeRequests(http);
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
