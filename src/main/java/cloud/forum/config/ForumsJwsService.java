package cloud.forum.config;

import com.naturalprogrammer.spring.lemon.commons.security.LemonJwsService;
import com.naturalprogrammer.spring.lemon.commons.security.UserDto;
import com.naturalprogrammer.spring.lemon.commonsweb.util.LecwUtils;
import com.nimbusds.jose.JOSEException;

import java.util.Map;
import java.util.Optional;

class ForumsJwsService extends LemonJwsService {
    ForumsJwsService(String secret) throws JOSEException {
        super(secret);
    }

    @Override
    public String createToken(String aud, String subject, Long expirationMillis, Map<String, Object> claimMap) {
        UserDto userDto = LecwUtils.currentUser();
        Map<String, Object> claims = Optional.ofNullable(userDto)
                .map(UserDto::getRoles)
                .map(roles -> {
                    claimMap.put("roles", roles);
                    return claimMap;
                })
                .orElse(claimMap);
        return super.createToken(aud, subject, expirationMillis, claims);
    }
}
