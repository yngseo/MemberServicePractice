package com.example.memberservicepractice.repository;

import com.example.memberservicepractice.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {

    public List<MemberDto> getList();

    public MemberDto getMemberById(String id);

}
