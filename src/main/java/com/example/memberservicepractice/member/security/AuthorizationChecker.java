package com.example.memberservicepractice.member.security;

import com.example.memberservicepractice.member.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationChecker {

    public boolean passwordStateCheck(Authentication authentication) { // 비밀번호 상태가 'I'일 시 접근 제한
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!(userDetails.getMember() instanceof MemberDto)) {
            return false;
        }
        if (userDetails.getMember().getPasswordState() == 'I') {
            return false;
        }
    return true;
    }

}
