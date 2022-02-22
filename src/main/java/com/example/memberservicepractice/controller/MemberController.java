package com.example.memberservicepractice.controller;

import com.example.memberservicepractice.dto.MemberDto;
import com.example.memberservicepractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

    @GetMapping("/main")
    public List<MemberDto> getList() {
        return memberService.getList();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 로그인 실패 페이지
    @GetMapping("/fail")
    public String accessDenied() {
        return "fail";
    }

    // 로그인 성공 페이지
    @GetMapping("/success")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDto memberDto = (MemberDto) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("info", memberDto.getId() + "의 " + memberDto.getName() + "님");      //유저 아이디
        return "success";
    }

    // 계정 생성 테스트
    @GetMapping("/create")
    public String create() {
        return "create";
    }

}
