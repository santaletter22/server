package com.project.santaletter.security.provider;

import com.project.santaletter.config.SecretsConfig;
import com.project.santaletter.domain.User;
import com.project.santaletter.security.info.NaverUserInfo;
import com.project.santaletter.security.info.OAuth2UserInfo;
import com.project.santaletter.security.session.SessionUser;
import com.project.santaletter.user.UserRepository;
import com.project.santaletter.util.CryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * OAuth 통한 로그인/회원가입 처리.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final CryptionService cryptionService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SecretsConfig secretsConfig;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        User user = processOAuth2User(oAuth2User);
        SessionUser sessionUser = new SessionUser(user);

        return new PrincipalDetails(sessionUser, oAuth2User.getAttributes());
    }

    private User processOAuth2User(OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));

        Optional<User> userOptional =
                userRepository.findByUsername(oAuth2UserInfo.getProvider() + oAuth2UserInfo.getProviderId());

        try {
            User user = saveIfEmpty(userOptional, oAuth2UserInfo);
            return user;

        } catch (Exception e) {
            throw new OAuth2AuthenticationException("Internal Server error at processOAUth2User method");
        }
    }

    private User saveIfEmpty(Optional<User> userOptional, OAuth2UserInfo oAuth2UserInfo) throws Exception {
        if (userOptional.isEmpty()) { //회원 가입 진행.
            User user = User.builder()
                    .username(oAuth2UserInfo.getProvider() + oAuth2UserInfo.getProviderId())
                    .password(encode(secretsConfig.getSecurityPasswordKey()))
                    .realName(oAuth2UserInfo.getName())
                    .role("ROLE_USER")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .phone(cryptionService.encrypt(oAuth2UserInfo.getPhone()))
                    .email(oAuth2UserInfo.getEmail())
                    .build();
            User savedEntity = userRepository.save(user);
            return savedEntity;
        }

        return userOptional.get();
    }

    private String encode(String text) {
        return bCryptPasswordEncoder.encode(text);
    }
}
