package com.serverstudy.todolist.service;

import com.serverstudy.todolist.domain.User;
import com.serverstudy.todolist.dto.request.UserReq.UserPatchNickname;
import com.serverstudy.todolist.dto.request.UserReq.UserPatchPassword;
import com.serverstudy.todolist.dto.request.UserReq.UserPost;
import com.serverstudy.todolist.dto.response.UserRes;
import com.serverstudy.todolist.exception.CustomException;
import com.serverstudy.todolist.repository.TodoRepository;
import com.serverstudy.todolist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.serverstudy.todolist.exception.ErrorCode.DUPLICATE_USER_EMAIL;
import static com.serverstudy.todolist.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public long join(UserPost userPost) {

        checkEmailDuplicated(userPost.getEmail());

        User user = userPost.toEntity();

        return userRepository.save(user).getId();
    }

    public void checkEmailDuplicated(String email) {

        if (userRepository.existsByEmail(email)) {
            throw new CustomException(DUPLICATE_USER_EMAIL);
        }
    }

    public UserRes get(Long userId) {

        User user = getUser(userId);

        return UserRes.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    @Transactional
    public long modifyNickname(UserPatchNickname userPatchNickname, Long userId) {

        User user = getUser(userId);

        user.modifyNickname(userPatchNickname);

        return user.getId();
    }

    @Transactional
    public long modifyPassword(UserPatchPassword userPatchPassword, Long userId) {

        User user = getUser(userId);

        user.modifyPassword(userPatchPassword);

        return user.getId();
    }

    @Transactional
    public void delete(Long userId) {

        User user = getUser(userId);

        // 투두 리스트 삭제
        todoRepository.deleteAll(todoRepository.findAllByUserId(userId));

        userRepository.delete(user);
    }

    private User getUser(Long userId) {

        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }
}
