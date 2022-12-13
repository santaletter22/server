package com.project.santaletter.letter.receive;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.santaletter.domain.Letter;
import com.project.santaletter.domain.User;
import com.project.santaletter.letter.receive.dto.ReceiveLetterDto;
import com.project.santaletter.letter.receive.dto.ReceiveLetterListDto;
import com.project.santaletter.security.provider.PrincipalDetails;
import com.project.santaletter.user.UserService;
import com.project.santaletter.util.CryptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ReceiveController {

    private final ReceiveService receiveService;
    private final UserService userService;
    private final CryptionService cryptionService;

    @GetMapping("/letter/count")
    public ResponseEntity getMyLetterSize(@RequestParam("phone") String phoneNumber) throws Exception {
        String encryptedPhoneNumber = cryptionService.encrypt(phoneNumber);
        Long size = receiveService.findMyLetterSize(encryptedPhoneNumber);

        JsonObject responseBody = new JsonObject();
        responseBody.addProperty("size", size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Gson().toJson(responseBody));
    }

    @GetMapping("/user/letter/count")
    public ResponseEntity getMyLetterSize(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Long userId = principalDetails.getId();

        User user = userService.findUser(userId);
        String phone = user.getPhone();

        Long size = receiveService.findMyLetterSize(phone);

        JsonObject responseBody = new JsonObject();
        responseBody.addProperty("size", size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Gson().toJson(responseBody));
    }

    @GetMapping("/user/letter/{id}")
    public ResponseEntity getMyLetter(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                      @PathVariable(value = "id") Long letterId) {
        Long userId = principalDetails.getId();

        try {
            ReceiveLetterDto receiveLetterDto = receiveService.findMyLetter(userId, letterId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new Gson().toJson(receiveLetterDto));
        } catch (IllegalAccessException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .build();
        }

    }

    @DeleteMapping("/user/letter/{id}")
    public ResponseEntity deleteMyLetter(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                      @PathVariable(value = "id") Long letterId) {
        receiveService.deleteLetter(letterId);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/user/letter/page/{startId}/{amount}")
    public ResponseEntity getMyLetterPage(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                          @PathVariable int startId,
                                          @PathVariable int amount) {
        List<Letter> pagedLetterList = receiveService.findMyLetterPage(startId, amount, principalDetails.getId());
        List<ReceiveLetterListDto> responseList = pagedLetterList.stream()
                .map(l -> new ReceiveLetterListDto(l))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Gson().toJson(responseList));
    }
}
