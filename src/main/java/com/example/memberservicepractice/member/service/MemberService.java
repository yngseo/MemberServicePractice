package com.example.memberservicepractice.member.service;

import com.example.memberservicepractice.common.Pagination.Criteria;
import com.example.memberservicepractice.member.dto.MemberDto;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface MemberService {

    public int getTotalAdminList(Criteria criteria, Integer levelSeq);

    public int getTotalClientList(Criteria criteria, String name);

    public List<MemberDto> getListByAdmin(Criteria criteria, Integer levelSeq); // 관리자 계정 전체 필터 조회

    public List<MemberDto> getListByClient(Criteria criteria, String name); // 고객사 계정 전체 조회

    public MemberDto getMemberById(String id); // 계정 상세 정보 조회

    public int updateMemberAccountState(String id); // 계정 승인

    public int updateMemberPasswordState(String id); // 비밀번호 초기화

    public boolean confirmPassword(String currentPw, String password); // 비밀번호 확인

    public int updatePassword(String currentPw, String password, String id); // 비밀번호 변경

    public int checkId(String id); // ID 중복 체크

    public void insertMember(MemberDto memberDto); // 계정 생성 요청

    public MemberDto getLoginMember (Authentication authentication); // 로그인 계정 정보

    public List<String> getClientList(); // 생성 페이지 내 고객사 리스트

}
