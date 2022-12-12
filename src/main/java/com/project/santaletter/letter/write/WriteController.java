package com.project.santaletter.letter.write;

import com.project.santaletter.domain.User;
import com.project.santaletter.letter.write.dto.WriteLetterDto;
import com.project.santaletter.letter.write.dto.WriteLetterReq;
import com.project.santaletter.security.provider.PrincipalDetails;
import com.project.santaletter.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WriteController {

    private final WriteService writeService;
    private final UserService userService;

    @PostMapping("/user/letter")
    public ResponseEntity postLetter(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @Valid @RequestBody WriteLetterReq writeLetterReq) {
        User user = userService.findUser(principalDetails.getId());

        if (user.getTicket() <= 0) { //모자가 부족해서 편지를 쓸 수 없음
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Not enough ticket");
        }

        user.useTicket(1);
        WriteLetterDto writeLetterDto = new WriteLetterDto(writeLetterReq, user);
        writeService.writeLetter(writeLetterDto);
        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
