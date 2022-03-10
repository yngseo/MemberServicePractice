package com.example.memberservicepractice.member.security;

import com.example.memberservicepractice.member.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationChecker {

    public boolean check(Authentication authentication) {
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
