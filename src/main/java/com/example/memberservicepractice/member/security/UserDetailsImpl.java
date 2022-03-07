package com.example.memberservicepractice.member.security;

import com.example.memberservicepractice.member.dto.MemberDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private MemberDto member;

    public MemberDto getMember() {
        return member;
    }

    public UserDetailsImpl(MemberDto member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getUserRole()));
    }

    // 시큐리티의 userName
    // -> 인증할 때 id를 봄
    @Override
    public String getUsername() {
        return member.getId();
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // 계정 만료 여부

    @Override
    public boolean isAccountNonLocked() {
        if (member.getApprovalDate() == null) {
            return false;
        }
        return true;
    } // 계정 잠금 여부

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }  // 계정 비밀번호 만료 여부

    @Override
    public boolean isEnabled() {
        return true;
    } // 계정 사용 가능 여부


}
