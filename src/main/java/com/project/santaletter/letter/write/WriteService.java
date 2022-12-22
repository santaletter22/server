package com.project.santaletter.letter.write;

import com.project.santaletter.domain.Letter;
import com.project.santaletter.domain.User;
import com.project.santaletter.letter.LetterRepository;
import com.project.santaletter.letter.write.dto.WriteLetterReq;
import com.project.santaletter.user.UserService;
import com.project.santaletter.util.CryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WriteService {

    private final UserService userService;
    private final CryptionService cryptionService;
    private final LetterRepository letterRepository;

    public void writeLetter(Long userId, WriteLetterReq writeLetterReq) throws IllegalAccessException {
        User user = userService.findUser(userId);

        if (user.getTicket() <= 0) {
            throw new IllegalAccessException("티겟이 부족합니다");
        }

        user.useTicket(1);
        try {
            String title = writeLetterReq.getTitle();
            String receiverPhone = cryptionService.encrypt(writeLetterReq.getReceiverPhone());
            String sender = writeLetterReq.getSender();
            String content = writeLetterReq.getContent();
            Letter letter = new Letter(title, content, true, false, receiverPhone, sender, user.getPhone(), user.getUsername());
            letterRepository.save(letter);
        } catch (Exception e) {
            //handle encryption error
            log.error("encryption error when writing letter");
        }
    }
}
