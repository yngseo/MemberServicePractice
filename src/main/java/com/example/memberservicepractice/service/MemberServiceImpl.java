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

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService, UserDetailsService {

    @Autowired
    MemberMapper memberMapper;

    public List<MemberDto> getListByAdmin() {
        return memberMapper.getListByAdmin();
    }

    public List<MemberDto> getListByAdmin(Integer levelSeq) {
        return memberMapper.getListByAdmin(levelSeq);
    }

    public List<MemberDto> getListByClient(String name) {
        return memberMapper.getListByClient(name);
    }

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        MemberDto memberDto = memberMapper.getMemberById(id);
        if (memberDto == null) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return memberDto;
    }

    public int updateMemberAccountState(String id) {
        return memberMapper.updateMemberAccountState(id);
    }

    public int updateMemberPasswordState(String id) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String randomPw = getRamdomPassword(10); // 난수 처리
        System.out.println("비밀번호 : " + randomPw);
        String password = passwordEncoder.encode(randomPw);
        return memberMapper.updateMemberPasswordState(password, id);
    }

    public int updatePassword(String currentPw, String password, String id) {
        MemberDto memberDto = memberMapper.getMemberById(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(currentPw, memberDto.getPassword())) {
            return memberMapper.updatePassword(passwordEncoder.encode(password), id);
        }
        return 0;
    }

    public int checkId(String id) {
        return memberMapper.checkId(id);
    }

    @Transactional(rollbackFor = Exception.class) // commit, rollback 자동 수행 (특정 예외 발생 시 rollback)
    public void insertMember(MemberDto memberDto) {
        String password = getRamdomPassword(10); // 난수 처리
        System.out.println("비밀번호 : " + password);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        memberDto.setPassword(passwordEncoder.encode(password));
        if (memberDto.getLevelSeq() == 2) {
            memberDto.setUserRole("ROLE_EMP");
        } else if (memberDto.getLevelSeq() == 3) {
            memberDto.setUserRole("ROLE_CLIENT");
        } else if (memberDto.getLevelSeq() == 4) {
            memberDto.setUserRole("ROLE_CLIENTEMP");
        }
        memberMapper.insertMember(memberDto);
    }

    public MemberDto getLoginMember(Authentication authentication) { //로그인된 회원 조회
        return authentication != null ? (MemberDto) authentication.getPrincipal() : null;
    }

    public List<String> getClientList() {
        return memberMapper.getClientList();
    }

    
    // 비밀번호 난수 생성 함수
    private String getRamdomPassword(int size) {
        char[] charSet = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&'};
        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());
        int idx = 0;
        int len = charSet.length;
        for (int i = 0; i < size; i++) { // idx = (int) (len * Math.random());
            idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
            sb.append(charSet[idx]);
        }
        return sb.toString();
    }


}
