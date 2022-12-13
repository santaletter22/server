package com.project.santaletter.letter.receive.dto;

import com.project.santaletter.domain.Letter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReceiveLetterListDto {

    private Long id;
    private String sender;
    private String title;
    private boolean locked;

    public ReceiveLetterListDto(Letter letter) {
        this.id = letter.getId();
        this.sender = letter.getSender();
        this.title = letter.getTitle();
        this.locked = letter.isLocked();
    }
}
