package com.example.memberservicepractice.service;

import com.example.memberservicepractice.dto.MemberDto;
import com.example.memberservicepractice.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    MemberMapper memberMapper;

    public List<MemberDto> getListByAdmin() {
        return memberMapper.getListByAdmin();
    }

    public List<MemberDto> getListByAdmin(Integer levelSeq) {return  memberMapper.getListByAdmin(levelSeq);}

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MemberDto memberDto = memberMapper.getMemberById(id);
        if(memberDto == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return memberDto;
    }

    public int checkId(String id) {
        return memberMapper.checkId(id);
    };

    @Transactional
    public void insertMember(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode("12345")); // 난수 처리 필요
        memberDto.setUserRole("ROLE_USER"); // 스프링 role 처리 필요
        memberMapper.insertMember(memberDto);
    }

    public MemberDto getLoginMember (Authentication authentication) { //로그인된 회원 조회
        return authentication != null ? (MemberDto) authentication.getPrincipal() : null;
    }

}
