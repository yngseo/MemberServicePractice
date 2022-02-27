package com.example.memberservicepractice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

@Data
public class MemberDto implements UserDetails {

    private Integer seq;
    @NotEmpty(message = "필수 입력 정보입니다.")
    @Length(min = 6, max = 20, message = "6~20자여야 합니다.")
    @Pattern(regexp = "[a-z]+[a-z0-9]{5,19}" , message = "영문자로 시작하는 영문자 또는 숫자로 이루어져야 합니다.")
    private String id;
    private String password;
    @NotEmpty(message = "필수 입력 정보입니다.")
    @Length(min = 2, max = 10, message = "2~10자여야 합니다.")
    private String name;
    @NotEmpty(message = "필수 입력 정보입니다.")
    @Email(message = "올바른 형식의 이메일 주소여야 합니다.")
    private String email;
    @NotNull(message = "필수 입력 정보입니다.")
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


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.userRole)); // list 오류 원인 예상
    }

    // 시큐리티의 userName
    // -> 인증할 때 id를 봄
    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}