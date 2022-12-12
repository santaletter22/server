package com.project.santaletter.letter.receive.dto;

import com.project.santaletter.domain.Letter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReceiveLetterListDto {

    private String sender;
    private String title;
    private String content;
    private boolean locked;

    public ReceiveLetterListDto(Letter letter) {
        this.sender = letter.getSender();
        this.title = letter.getTitle();
        this.content = letter.getContent();
        this.locked = letter.isLocked();
    }
}
