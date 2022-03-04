package com.example.memberservicepractice.service;

import com.example.memberservicepractice.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MemberService {

    public List<MemberDto> getListByAdmin(); // 관리자 계정 전체 조회

    public List<MemberDto> getListByAdmin(Integer levelSeq); // 관리자 계정 전체 필터 조회

    public List<MemberDto> getListByClient(String name); // 고객사 계정 전체 조회

    public MemberDto getMemberById(String id); // 계정 상세 정보 조회

    public int updateMemberAccountState(String id); // 계정 승인

    public int updateMemberPasswordState(String id); // 비밀번호 초기화 해제

    public int updatePassword(String currentPw, String password, String id); // 비밀번호 변경

    public int checkId(String id); // ID 중복 체크

    public void insertMember(MemberDto memberDto); // 계정 생성 요청

    public MemberDto getLoginMember (Authentication authentication); // 로그인 계정 정보

    public List<String> getClientList(); // 생성 페이지 내 고객사 리스트

}
