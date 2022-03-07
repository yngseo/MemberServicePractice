package com.example.memberservicepractice.member.controller;

import com.example.memberservicepractice.common.Pagination.Criteria;
import com.example.memberservicepractice.common.Pagination.PageDto;
import com.example.memberservicepractice.member.dto.MemberDto;
import com.example.memberservicepractice.member.security.UserDetailsServiceImpl;
import com.example.memberservicepractice.member.service.MemberService;
import com.example.memberservicepractice.member.security.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;
    @Autowired
    UserDetailsServiceImpl userDetailService;

    @RequestMapping(value = {"/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/main")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto);      //유저 아이디
        return "main";
    }

    @GetMapping("/myInfo")
    public String myInfoForm(Model model, Authentication authentication) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto);
        return "myInfo";
    }

    @GetMapping("/password/{id}")
    public String updatePasswordForm(@ModelAttribute("member") MemberDto memberDto, @PathVariable("id") String id) {
        /*memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("loginId", memberDto.getId());*/
        return "password";
    }

    @PutMapping("/password/{id}")
    public String updatePassword(Model model, @PathVariable("id") String id, @RequestParam("currentPw") String currentPw, @RequestParam("password") String password, @Validated(ValidationGroups.password.class) @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult) {
        /*memberDto = memberService.getLoginMember(authentication);
        String id = memberDto.getId();*/
        if (memberService.updatePassword(currentPw, password, id) == 0) { // 수정 필요
            model.addAttribute("message","비밀번호가 일치하지 않습니다.");
            return "password";
        } else if (bindingResult.hasErrors()) {
            return "password";
        }
        memberService.updatePassword(currentPw, password, id);
        return "redirect:/login";
    }

    // 계정 조회 페이지
    @GetMapping("list")
    public String memberList (Model model, Authentication authentication, @ModelAttribute("criteria") Criteria criteria) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto);
        String name = memberDto.getName();
        if (memberDto.getLevelSeq() == 1) {
            model.addAttribute("list", memberService.getListByAdmin(criteria));
            model.addAttribute("pageMaker", new PageDto(memberService.getTotal(criteria), 10, criteria));
        } else if (memberDto.getLevelSeq() == 3) {
            model.addAttribute("list", memberService.getListByClient(name));
        }
        return "member/list";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable("id") String id, Model model, Authentication authentication) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto);
        model.addAttribute("member", memberService.getMemberById(id));
        return "member/get";
    }

    @GetMapping("/filter")
    @JsonIgnore
    @ResponseBody
    public List<MemberDto> getListUsingFilter (@RequestParam("levelSeq") Integer levelSeq, Model model, Authentication authentication) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("member", memberDto);
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

    // 계정 생성 페이지
    @GetMapping("/create")
    public String createForm(Model model, Authentication authentication, @ModelAttribute("member") MemberDto memberDto) {
        memberDto = memberService.getLoginMember(authentication); // 줄이기
        model.addAttribute("info", memberDto);
        model.addAttribute("client", memberService.getClientList());
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
            model.addAttribute("info", memberDto);      //유저 아이디
            model.addAttribute("client", memberService.getClientList());
            return "member/create";
        }
        memberService.insertMember(memberDto);
        return "redirect:/list";
    }


}