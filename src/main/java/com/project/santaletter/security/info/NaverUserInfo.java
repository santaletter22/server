package com.project.santaletter.security.info;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProviderId() {
        return (String) attributes.get("id");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    /**
     * get last 4 digits of the phone
     * @return
     */
    @Override
    public String getPhone() {
        String mobileString = (String) attributes.get("mobile");
        String onlyNumbers = mobileString.replace("-", "");
        return onlyNumbers;
    }
}