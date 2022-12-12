package com.project.santaletter.letter.write;

import com.project.santaletter.domain.Letter;
import com.project.santaletter.letter.LetterRepository;
import com.project.santaletter.letter.write.dto.WriteLetterDto;
import com.project.santaletter.letter.write.dto.WriteLetterReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WriteService {

    private final LetterRepository letterRepository;

    public void writeLetter(WriteLetterDto writeLetterDto) {
        Letter letter = writeLetterDto.toEntity();
        letterRepository.save(letter);
    }
}
