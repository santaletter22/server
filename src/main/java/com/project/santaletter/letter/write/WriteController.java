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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class WriteController {

    private final WriteService writeService;

    @PostMapping("/user/letter")
    public ResponseEntity postLetter(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                     @Valid @RequestBody WriteLetterReq writeLetterReq) {
        try {
            writeService.writeLetter(principalDetails.getId(), writeLetterReq);
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Not enough ticket");
        }
    }
}
