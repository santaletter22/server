package com.project.santaletter.security.info;

public interface OAuth2UserInfo {
    String getProviderId();
    String getProvider();
    String getName();
    String getPhone();

    String getEmail();

}
