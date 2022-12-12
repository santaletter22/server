package com.project.santaletter.letter.write.dto;

import com.project.santaletter.domain.Letter;
import com.project.santaletter.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class WriteLetterDto {
    private String receiverPhone;
    private String sender;
    private String title;
    private String content;
    private String senderPhone;
    private String senderUsername;

    public WriteLetterDto(WriteLetterReq writeLetterReq, User sendUser) {
        this.receiverPhone = writeLetterReq.getReceiverPhone();
        this.sender = writeLetterReq.getSender();
        this.title = writeLetterReq.getTitle();
        this.content = writeLetterReq.getContent();
        this.senderPhone = sendUser.getPhone();
        this.senderUsername = sendUser.getUsername();
    }

    public Letter toEntity() {
        Letter letter = new Letter(title, content, true, false, receiverPhone, sender, senderPhone, senderUsername);
        return letter;
    }
}
