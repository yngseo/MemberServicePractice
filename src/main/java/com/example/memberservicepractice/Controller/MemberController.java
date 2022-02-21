package com.example.memberservicepractice.Controller;

import com.example.memberservicepractice.Dto.MemberDto;
import com.example.memberservicepractice.Repository.MemberRepository;
import com.example.memberservicepractice.Service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/main")
    public List<MemberDto> getList() {
        return memberRepository.getList();
    }

/*    @PostMapping("/login")
    public int login() {
        return memberRepository.login();
    }*/

}
