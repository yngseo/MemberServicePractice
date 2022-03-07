package com.example.memberservicepractice.member.security;

import com.example.memberservicepractice.member.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationChecker {

    public String check(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!(userDetails.getMember() instanceof MemberDto)) {
            return null;
        }
        if (userDetails.getMember().getPasswordState() == 'I') {
            return null;
        }
    return userDetails.getMember().getUserRole().substring(5);
    }

}
