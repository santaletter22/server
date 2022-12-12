package com.project.santaletter.security.session;

import com.project.santaletter.domain.User;
import lombok.Data;
import java.io.Serializable;

@Data
public class SessionUser implements Serializable {

    private Long id;
    private String username;
    private String phone;
    private String role;

    public SessionUser(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.phone = user.getPhone();
        this.role = user.getRole();
    }
}
