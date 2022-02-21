package com.example.memberservicepractice.Service;

import com.example.memberservicepractice.Dto.MemberDto;
import com.example.memberservicepractice.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MemberServiceImpl implements MemberService{

    @Autowired
    MemberRepository memberRepository;

    @Override
    public List<MemberDto> getList() {
        return memberRepository.getList();
    }

    @Override
    public MemberDto login(String id, String password) {
        return memberRepository.login(id, password);
    }


}
