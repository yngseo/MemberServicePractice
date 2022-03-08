package com.example.memberservicepractice.member.service;

import com.example.memberservicepractice.common.Pagination.Criteria;
import com.example.memberservicepractice.member.dto.MemberDto;
import com.example.memberservicepractice.member.security.UserDetailsImpl;
import com.example.memberservicepractice.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

/*    @Override
    public int getTotal(Criteria criteria) {
        return memberRepository.getTotal(criteria);
    }*/

    public int getTotalAdminList(Criteria criteria, Integer levelSeq) { return  memberRepository.getTotalAdminList(criteria, levelSeq); }

    @Override
    public int getTotalClientList(Criteria criteria, String name) {
        return memberRepository.getTotalClientList(criteria, name);
    }

/*    @Override
    public List<MemberDto> getListByAdmin(Criteria criteria) {
        return memberRepository.getListByAdmin(criteria);
    }*/

    @Override
    public List<MemberDto> getListByAdmin(Criteria criteria, Integer levelSeq) {
        return memberRepository.getListByAdmin(criteria, levelSeq);
    }

    @Override
    public List<MemberDto> getListByClient(Criteria criteria, String name) {
        return memberRepository.getListByClient(criteria, name);
    }

    @Override
    public MemberDto getMemberById(String id) {
        return memberRepository.getMemberById(id);
    }

    @Override
    public int updateMemberAccountState(String id) {
        return memberRepository.updateMemberAccountState(id);
    }

    @Override
    public int updateMemberPasswordState(String id) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // bean 설정
        String randomPw = getRamdomPassword(10); // 난수 처리
        System.out.println("비밀번호 : " + randomPw);
        String password = passwordEncoder.encode(randomPw);
        return memberRepository.updateMemberPasswordState(password, id);
    }

    @Override
    public int updatePassword(String currentPw, String password, String id) {
        MemberDto memberDto = memberRepository.getMemberById(id); // 기능 분리
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (passwordEncoder.matches(currentPw, memberDto.getPassword())) {
            return memberRepository.updatePassword(passwordEncoder.encode(password), id);
        }
        return 0; // boolean
    }

    @Override
    public int checkId(String id) {
        return memberRepository.checkId(id);
    }

    @Override
   /* @Transactional(rollbackFor = Exception.class)*/ // commit, rollback 자동 수행 (특정 예외 발생 시 rollback)
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
        memberRepository.insertMember(memberDto);
    }

    @Override
    public MemberDto getLoginMember(Authentication authentication) { //로그인된 회원 조회
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        MemberDto member = userDetails.getMember();
        return authentication != null ? member : null;
    }

    @Override
    public List<String> getClientList() {
        return memberRepository.getClientList();
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
