package com.example.memberservicepractice.mapper;

import com.example.memberservicepractice.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.Authentication;

import java.util.List;

@Mapper
public interface MemberMapper {

    public List<MemberDto> getListByAdmin();

    public List<MemberDto> getListByAdmin(Integer levelSeq);

    public MemberDto getMemberById(String id);

    public int checkId(String id);

    public int insertMember(MemberDto memberDto);

}
