package com.project.santaletter.letter.write.dto;

import com.project.santaletter.domain.Letter;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class WriteLetterReq {
    @NotNull
    private String receiverPhone;

    @NotNull
    private String sender;

    @NotNull
    private String title;

    @NotNull
    private String content;

}
