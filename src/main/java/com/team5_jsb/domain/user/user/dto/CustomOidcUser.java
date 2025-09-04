package com.team5_jsb.domain.user.user.dto;

import com.team5_jsb.domain.user.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOidcUser implements OidcUser {
    private final User user;
    private final OidcUser oidcUser;

    public CustomOidcUser(User user, OidcUser oidcUser) {
        this.user = user;
        this.oidcUser = oidcUser;
    }

    @Override
    public String getName() {
        // Spring Security에서 사용할 이름 - DB의 username 반환
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Google scope 대신 애플리케이션의 권한 반환
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public Map<String, Object> getAttributes() {
        // Google에서 받은 원본 속성들 반환
        return oidcUser.getAttributes();
    }

    @Override
    public OidcIdToken getIdToken() {
        // Google OIDC ID Token 반환
        return oidcUser.getIdToken();
    }

    @Override
    public OidcUserInfo getUserInfo() {
        // Google OIDC UserInfo 반환
        return oidcUser.getUserInfo();
    }

    @Override
    public Map<String, Object> getClaims() {
        // Google ID Token의 모든 claims 반환
        return oidcUser.getClaims();
    }

    // User 엔티티에 접근
    public User getUser() {
        return user;
    }

    public Long getUserId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }
}