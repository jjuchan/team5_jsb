package com.team5_jsb.domain.user.user.service;

import com.team5_jsb.domain.user.user.dto.CustomOidcUser;
import com.team5_jsb.domain.user.user.entity.AuthProvider;
import com.team5_jsb.domain.user.user.entity.User;
import com.team5_jsb.domain.user.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class CustomOidcUserService extends OidcUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        // 부모 클래스 loadUser 호출하여 OIDC 사용자 정보 가져오기
        OidcUser oidcUser = super.loadUser(userRequest);
        
        // OAuth2 제공자 확인 (현재는 Google만 지원)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        // 사용자 정보 추출 및 처리
        return processOidcUser(registrationId, oidcUser);
    }
    
    private OidcUser processOidcUser(String registrationId, OidcUser oidcUser) {
        
        // 현재 Google만 연동됨, 추후 확장 예정
        if (!"google".equals(registrationId)) {
            throw new OAuth2AuthenticationException("현재는 Google 로그인만 지원합니다.");
        }
        
        // Google 사용자 정보 추출 (ID Token에서)
        Map<String, Object> attributes = oidcUser.getAttributes();
        String googleId = oidcUser.getSubject();  // OIDC subject (Google 고유 ID)
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String picture = (String) attributes.get("picture");
        
        // 기존 사용자 확인
        Optional<User> existingUser = userRepository.findByEmail(email);
        
        User user = existingUser.orElseGet(() -> {
            return createGoogleUser(googleId, email, name, picture);
        });
        
        // 기존 사용자인 경우 정보 업데이트
        if (user.getProvider() == AuthProvider.LOCAL) {
            // 로컬 계정과 Google 계정 연동
            updateLocalUserToGoogle(user, googleId);
        }
        
        // CustomOidcUser 를 반환 (우리 DB User 정보를 Principal로 사용할 수 있도록 매핑)
        return new CustomOidcUser(user, oidcUser);
    }
    
    private User createGoogleUser(String googleId, String email, String name, String picture) {
        
        try {
            User user = new User();
            user.setUsername(name);  // Google name 필드를 username으로 사용
            user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            user.setEmail(email);
            user.setProvider(AuthProvider.GOOGLE);
            user.setProviderId(googleId);
            
            User saved = userRepository.save(user);
            return saved;
        } catch (Exception e) {
            log.debug("OIDC 소셜 계정 저장 실패: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    private void updateLocalUserToGoogle(User user, String googleId) {
        user.setProvider(AuthProvider.GOOGLE);
        user.setProviderId(googleId);
        userRepository.save(user);
    }
}