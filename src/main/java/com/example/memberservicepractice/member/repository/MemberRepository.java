package com.example.memberservicepractice.member.repository;

import com.example.memberservicepractice.common.Pagination.Criteria;
import com.example.memberservicepractice.member.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberRepository {

    /*public int getTotal(Criteria criteria);*/

    public int getTotalAdminList(Criteria criteria, Integer levelSeq);

    public int getTotalClientList(Criteria criteria, String name);

    /*public List<MemberDto> getListByAdmin(@Param("criteria") Criteria criteria);*/

    public List<MemberDto> getListByAdmin(@Param("criteria") Criteria criteria, @Param("levelSeq") Integer levelSeq);

    public List<MemberDto> getListByClient(@Param("criteria") Criteria criteria, @Param("name") String name);

    public MemberDto getMemberById(String id);

    public int updateMemberAccountState(String password, String id);

    public int updateMemberPasswordState(String password, String id);

    public int updatePassword(String password, String id);

    public int checkId(String id);

    public int insertMember(MemberDto memberDto);

    public List<String> getClientList();

}
