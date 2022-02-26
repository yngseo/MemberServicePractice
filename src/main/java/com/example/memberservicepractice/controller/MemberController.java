package com.example.memberservicepractice.controller;

import com.example.memberservicepractice.dto.MemberDto;
import com.example.memberservicepractice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

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
    @GetMapping("/main")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto.getId() + "의 " + memberDto.getName() + "님");      //유저 아이디
        model.addAttribute("level", memberDto.getUserRole());
        return "main";
    }

    // 계정 조회 페이지
    @GetMapping("/list")
    public String memberList (Model model) {
        model.addAttribute("list", memberService.getListByAdmin());
        return "member/list";
    }

    @GetMapping("/list/{id}")
    public String get(@PathVariable("id") String id, Model model) {
        model.addAttribute("member", memberService.loadUserByUsername(id));
        return "member/get";
    }

    @GetMapping("/filter")
    @ResponseBody
    public List<MemberDto> getListUsingFilter (@RequestParam("levelSeq") Integer levelSeq) {
        return memberService.getListByAdmin(levelSeq);
    }

    @PutMapping("/approval")
    @ResponseBody
    public int updateMemberAccountState(@RequestParam("id") String id) {
        return memberService.updateMemberAccountState(id);
    }

    @PutMapping("/initialize")
    @ResponseBody
    public int updateMemberPasswordState(@RequestParam("id") String id) {
        return memberService.updateMemberPasswordState(id);
    }

    @GetMapping("/password")
    public String updatePasswordForm() {
        //MemberDto memberDto = memberService.getLoginMember(authentication);
        //model.addAttribute("id", memberDto.getId());
        return "password";
    }

    @PutMapping("/modifyPassword")
    @ResponseBody
    public int updatePassword(Authentication authentication, @RequestParam("password") String password, @RequestParam("newPw") String newPw) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        String id = memberDto.getId();
        return memberService.updatePassword(password, newPw, id);
    }

    // 계정 생성 페이지
    @GetMapping("/create")
    public String createForm(Model model, Authentication authentication, @ModelAttribute("member") MemberDto memberDto) {
        memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto.getId());      //유저 아이디
        return "member/create";
    }

    // ID 중복 체크
    @PostMapping("/checkId")
    @ResponseBody
    public int checkId(String id) {
        return memberService.checkId(id);
    }

    // 계정 생성 DB insert
    @PostMapping("/create")
    public String create(@ModelAttribute("member") @Valid MemberDto memberDto, BindingResult bindingResult, Model model, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            memberDto = memberService.getLoginMember(authentication);
            model.addAttribute("info", memberDto.getId());      //유저 아이디
            return "member/create";
        }
        memberService.insertMember(memberDto);
        return "member/list";
    }


}
