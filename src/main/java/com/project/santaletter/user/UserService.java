package com.project.santaletter.user;

import com.project.santaletter.domain.User;
import com.project.santaletter.error.ErrorMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new NoSuchElementException(ErrorMsg.userNotFoundMsg(userId));
        }

        return userOptional.get();
    }

    public void addTicketToUser(Long userId, int amount) {
        User user = findUser(userId);
        user.addTicket(amount);
    }
}
