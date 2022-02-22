package com.example.memberservicepractice.service;

import com.example.memberservicepractice.dto.MemberDto;
import com.example.memberservicepractice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService implements UserDetailsService {

    @Autowired
    MemberRepository memberRepository;

    public List<MemberDto> getList() {
        return memberRepository.getList();
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MemberDto memberDto = memberRepository.getMemberById(id);
        if(memberDto == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return memberDto;
    }

}
