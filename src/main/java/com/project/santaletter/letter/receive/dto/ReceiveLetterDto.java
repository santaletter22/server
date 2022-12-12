package com.project.santaletter.letter.receive.dto;

import com.project.santaletter.domain.Letter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReceiveLetterDto {

    private String sender;
    private String datetime;
    private String title;
    private String content;

    public ReceiveLetterDto(Letter letter) {
        this.sender = letter.getSender();
        this.datetime = letter.getCreateDate().toString();
        this.title = letter.getTitle();
        this.content = letter.getContent();
    }
}
