package com.example.memberservicepractice.member.dto;

import com.example.memberservicepractice.member.security.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
public class MemberDto { // 유효성 검사 분리하기

    private Integer seq;
    @NotEmpty(message = "필수 입력 정보입니다.")
    @Length(min = 6, max = 20, message = "6~20자여야 합니다.")
    @Pattern(regexp = "[a-z]+[a-z0-9]{5,19}" , message = "영문자로 시작하는 영문자 또는 숫자로 이루어져야 합니다.")
    private String id;
    @NotEmpty(message = "필수 입력 정보입니다.", groups = ValidationGroups.password.class)
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.", groups = ValidationGroups.password.class)
    private String password;
    @NotEmpty(message = "필수 입력 정보입니다.")
    @Length(min = 2, max = 10, message = "2~10자여야 합니다.")
    private String name;
    @NotEmpty(message = "필수 입력 정보입니다.")
    @Email(message = "올바른 형식의 이메일 주소여야 합니다.")
    private String email;
    @NotNull(message = "필수 선택 정보입니다.")
    private Integer levelSeq;
    private String applicant;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date modifyDate;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private Date approvalDate;
    private Character accountState;
    private Character passwordState;
    private String phone;
    private String remark;
    private String ceo;
    private String client;
    private String department;
    private String jobGrade;
    private String userRole;

    private LevelDto levelDto;

}
