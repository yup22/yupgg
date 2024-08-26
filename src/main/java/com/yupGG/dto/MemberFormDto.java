package com.yupGG.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MemberFormDto {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;
    @NotEmpty(message = "아이디(이메일)을 입력해주세요.")
    @Email
    private String email;
    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, message = "안전을 위해 비밀번호는 8자 이상 입력해주세요.")
    private String password;
    @NotEmpty(message = "비밀번호 확인은 필수 입력 항목입니다.")
    private String passwordConfirm;

}
