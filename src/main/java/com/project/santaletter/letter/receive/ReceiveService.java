package com.project.santaletter.letter.receive;

import com.project.santaletter.domain.Letter;
import com.project.santaletter.domain.User;
import com.project.santaletter.letter.LetterRepository;
import com.project.santaletter.letter.receive.dto.ReceiveLetterDto;
import com.project.santaletter.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReceiveService {

    private final UserService userService;
    private final LetterRepository letterRepository;

    /**
     * visitor can check their letter size
     * @param receiverPhoneNumber: encrypted phone number
     * @return size
     */
    public Long findMyLetterSize(String receiverPhoneNumber) {
        Long size = letterRepository.countByReceiverPhone(receiverPhoneNumber);
        return size;
    }

    public ReceiveLetterDto findMyLetter(Long userId, Long letterId) throws IllegalAccessException {
        Optional<Letter> letterOptional = letterRepository.findById(letterId);
        Letter letter = letterOptional.orElseThrow(
                () -> new NoSuchElementException("no letter found for this letter id : " + letterId)
        );

        User user = userService.findUser(userId);

        if(letter.isLocked()) {
            if (user.getTicket() <= 0) {
                throw new IllegalAccessException("티켓이 부족합니다");
            }
            user.useTicket(1);
            letter.setLocked(false);
        }

        ReceiveLetterDto receiveLetterDto = new ReceiveLetterDto(letter);
        return receiveLetterDto;
    }

    public List<Letter> findMyLetterPage(int startId, int amount, Long userId) {
        User user = userService.findUser(userId);
        List<Letter> receivedLetterList = letterRepository.findAllByReceiverPhoneAndDeleted(user.getPhone(), false);

        if (startId >= receivedLetterList.size()) {
            throw new NoSuchElementException("problem");
        }

        List<Letter> pagedLetter = receivedLetterList.subList(startId, Math.min(receivedLetterList.size(), startId + amount));
        return pagedLetter;
    }

    public void deleteLetter(Long letterId) {
        Optional<Letter> letterOptional = letterRepository.findById(letterId);
        Letter letter = letterOptional.orElseThrow(
                () -> new NoSuchElementException("no letter found for this letter id : " + letterId)
        );
        letter.delete();
    }
}
