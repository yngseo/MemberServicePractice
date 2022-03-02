package com.example.memberservicepractice.service;

import com.example.memberservicepractice.dto.MemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface MemberService {

    public List<MemberDto> getListByAdmin();

    public List<MemberDto> getListByAdmin(Integer levelSeq);

    public List<MemberDto> getListByClient(String name);

    public UserDetails loadUserByUsername(String id);

    public int updateMemberAccountState(String id);

    public int updateMemberPasswordState(String id);

    public int updatePassword(String currentPw, String password, String id);

    public int checkId(String id);

    public void insertMember(MemberDto memberDto);

    public MemberDto getLoginMember (Authentication authentication);

    public List<String> getClientList();

}
