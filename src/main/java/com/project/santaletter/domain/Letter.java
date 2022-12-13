package com.project.santaletter.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Letter extends BaseEntity{

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String title;

    @NotNull
    @Column(columnDefinition = "text")
    private String content;

    @NotNull
    private boolean locked;

    @NotNull
    private boolean deleted;

    @NotNull
    private String receiverPhone;

    @NotNull
    private String sender;

    @NotNull
    private String senderPhone;

    @NotNull
    private String senderUsername;

    @Builder
    public Letter(String title, String content, boolean locked, boolean deleted, String receiverPhone, String sender, String senderPhone, String senderUsername) {
        this.title = title;
        this.content = content;
        this.locked = locked;
        this.deleted = deleted;
        this.receiverPhone = receiverPhone;
        this.sender = sender;
        this.senderPhone = senderPhone;
        this.senderUsername = senderUsername;
    }

    public void  delete() {
        this.deleted = true;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
