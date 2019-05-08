package cloud.forum.config;

import com.naturalprogrammer.spring.lemon.commons.LemonProperties;
import com.naturalprogrammer.spring.lemon.commons.security.BlueTokenService;
import com.naturalprogrammer.spring.lemon.security.LemonJpaSecurityConfig;
import com.nimbusds.jose.JOSEException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.stereotype.Component;

//Configuration of The SpringSecurity module, enabling us authentication and authorization features
@Component
public class SecurityConfig extends LemonJpaSecurityConfig {

    @Override
    protected void authorizeRequests(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/forum/create/**", "/comment/update/**", "/post/update/**")
                .authenticated();
        super.authorizeRequests(http);
    }

    @Bean
    public BlueTokenService blueTokenService(LemonProperties properties) throws JOSEException {
        return new ForumsJwsService(properties.getJwt().getSecret());
    }
}
