package com.serverstudy.todolist.domain;

import com.serverstudy.todolist.domain.enums.Role;
import com.serverstudy.todolist.dto.request.UserReq.UserPatchNickname;
import com.serverstudy.todolist.dto.request.UserReq.UserPatchPassword;
import com.serverstudy.todolist.exception.CustomException;
import com.serverstudy.todolist.exception.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.serverstudy.todolist.exception.ErrorCode.BAD_PASSWORD;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column
    @NotNull
    private String password;

    @Column
    @NotNull
    private String nickname;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Builder
    private User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.roles = new HashSet<>();
        addRole(Role.USER);
    }

    public void modifyNickname(UserPatchNickname userPatchNickname) {
        this.nickname = userPatchNickname.getNickname();
    }

    public void modifyPassword(UserPatchPassword userPatchPassword) {
        // 기존 비밀번호와 같지 않으면 throw
        if (!Objects.equals(this.password, userPatchPassword.getExistingPassword())) {
            throw new CustomException(BAD_PASSWORD);
        }
        this.password = userPatchPassword.getNewPassword();
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

}
