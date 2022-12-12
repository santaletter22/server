package com.project.santaletter.security.provider;

import com.project.santaletter.security.session.SessionUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements Serializable, OAuth2User {

    private SessionUser sessionUser;
    private Map<String, Object> attributes;

    public PrincipalDetails(SessionUser sessionUser, Map<String, Object> attributes) {
        this.sessionUser = sessionUser;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<GrantedAuthority>();
        collect.add(()-> sessionUser.getRole());
        return collect;
    }

    @Override
    public String getName() {
        return sessionUser.getUsername();
    }

    public Long getId() {
        return sessionUser.getId();
    }
}
