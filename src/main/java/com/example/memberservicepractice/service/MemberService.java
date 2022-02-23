package com.example.memberservicepractice.service;

import com.example.memberservicepractice.dto.MemberDto;
import com.example.memberservicepractice.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public int checkId(String id) {
        return memberRepository.checkId(id);
    };

    @Transactional
    public void insertMember(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode("12345")); // 난수 처리 필요
        memberDto.setUserRole("ROLE_USER"); // 스프링 role 처리 필요
        memberRepository.insertMember(memberDto);
    }

}
