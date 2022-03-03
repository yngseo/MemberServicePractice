package com.example.memberservicepractice.security;

import com.example.memberservicepractice.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationChecker {

    public String check(Authentication authentication) {
        MemberDto memberDto = (MemberDto) authentication.getPrincipal();
        if (!(memberDto instanceof MemberDto)) {
            return null;
        }
        if (memberDto.getPasswordState()=='I') {
            return null;
        }
    return memberDto.getUserRole().substring(5);
    }

}
