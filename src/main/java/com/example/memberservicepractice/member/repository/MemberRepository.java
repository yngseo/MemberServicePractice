package com.example.memberservicepractice.member.repository;

import com.example.memberservicepractice.common.Pagination.Criteria;
import com.example.memberservicepractice.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberRepository {

    public int getTotal(Criteria criteria);

    public List<MemberDto> getListByAdmin(Criteria criteria);

    public List<MemberDto> getListByAdmin(Integer levelSeq);

    public List<MemberDto> getListByClient(String name);

    public MemberDto getMemberById(String id);

    public int updateMemberAccountState(String id);

    public int updateMemberPasswordState(String password, String id);

    public int updatePassword(String password, String id);

    public int checkId(String id);

    public int insertMember(MemberDto memberDto);

    public List<String> getClientList();

}
