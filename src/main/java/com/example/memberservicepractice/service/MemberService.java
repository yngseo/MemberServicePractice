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

    public List<MemberDto> getListByClient() {return memberMapper.getListByClient();}

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MemberDto memberDto = memberMapper.getMemberById(id);
        if(memberDto == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return memberDto;
    }

    public int updateMemberAccountState(String id) {
        return memberMapper.updateMemberAccountState(id);
    }

    public int updateMemberPasswordState(String id) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("00000"); // 난수 처리 필요
        return memberMapper.updateMemberPasswordState(password, id);
    }

    public int updatePassword(String currentPw, String password, String id) {
        MemberDto memberDto = memberMapper.getMemberById(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(currentPw, memberDto.getPassword())) {
            return memberMapper.updatePassword(passwordEncoder.encode(password), id);
        }
        return 0;
    };

    public int checkId(String id) {
        return memberMapper.checkId(id);
    };

    @Transactional
    public void insertMember(MemberDto memberDto){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode("12345")); // 난수 처리 필요
        if (memberDto.getLevelSeq() == 2) {
            memberDto.setUserRole("ROLE_EMP");
        } else if (memberDto.getLevelSeq() == 3) {
            memberDto.setUserRole("ROLE_CLIENT");
        } else if (memberDto.getLevelSeq() == 4) {
            memberDto.setUserRole("ROLE_CLIENTEMP");
        }
        memberMapper.insertMember(memberDto);
    }

    public MemberDto getLoginMember (Authentication authentication) { //로그인된 회원 조회
        return authentication != null ? (MemberDto) authentication.getPrincipal() : null;
    }

    public String getClientList() {return memberMapper.getClientList();}

}
