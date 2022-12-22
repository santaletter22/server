package com.project.santaletter.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull //username = provider + providerId, 유저 분별을 위함
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String realName;

    @NotNull
    private String provider;

    @NotNull
    private String providerId;

    @NotNull
    private String phone;

    @NotNull
    private String email;

    @NotNull
    private String role;

    @NotNull
    private int ticket;

    @Builder
    public User(String username, String password, String realName, String provider, String providerId, String phone, String email, String role) {
        this.username = username;
        this.password = password;
        this.realName = realName;
        this.provider = provider;
        this.providerId = providerId;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.ticket = 5;
    }

    public void useTicket(int amount) {
        this.ticket -= amount;
    }

    public void addTicket(int amount) {
        this.ticket += amount;
    }
}
