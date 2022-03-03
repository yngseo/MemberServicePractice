package com.example.memberservicepractice.security;

import com.example.memberservicepractice.dto.MemberDto;
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
        System.out.println(userDetails.getAuthorities());
    return userDetails.getMember().getUserRole().substring(5);
    }

}
