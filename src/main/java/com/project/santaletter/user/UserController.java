package com.project.santaletter.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.project.santaletter.domain.User;
import com.project.santaletter.security.provider.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/ticket/count")
    public ResponseEntity getTicketCount(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = userService.findUser(principalDetails.getId());

        JsonObject response = new JsonObject();
        response.addProperty("ticket", user.getTicket());

        return ResponseEntity.status(HttpStatus.OK)
                .body(new Gson().toJson(response));
    }

    @PatchMapping("/user/ticket/{amount}")
    public ResponseEntity patchTicketAmount(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                            @PathVariable int amount) {
        User user = userService.findUser(principalDetails.getId());
        user.addTicket(amount);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }
}
