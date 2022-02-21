package com.example.memberservicepractice.Repository;

import com.example.memberservicepractice.Dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {

    public List<MemberDto> getList();

    public MemberDto login(String id, String password);

}
