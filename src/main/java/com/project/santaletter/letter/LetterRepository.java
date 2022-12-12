package com.project.santaletter.letter;

import com.project.santaletter.domain.Letter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long> {
    Long countByReceiverPhone(String receiverPhone);

    List<Letter> findAllByReceiverPhoneAndDeleted(String receiverPhone, boolean deleted);

}
