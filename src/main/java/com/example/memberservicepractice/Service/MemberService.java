package com.example.memberservicepractice.Service;

import com.example.memberservicepractice.Dto.MemberDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MemberService {

    public List<MemberDto> getList();

    public MemberDto login(String id, String password);

}
