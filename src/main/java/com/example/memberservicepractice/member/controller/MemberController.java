package com.example.memberservicepractice.member.controller;

import com.example.memberservicepractice.common.pagination.Criteria;
import com.example.memberservicepractice.common.pagination.PageDto;
import com.example.memberservicepractice.member.dto.MemberDto;
import com.example.memberservicepractice.member.service.MemberService;
import com.example.memberservicepractice.member.security.ValidationGroups;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class MemberController {

    @Autowired
    MemberService memberService;

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
        return "password";
    }

    @PutMapping("/password/{id}")
    public String updatePassword(Model model, Authentication authentication, @PathVariable("id") String id, @RequestParam("currentPw") String currentPw, @RequestParam("password") String password, @Validated(ValidationGroups.password.class) @ModelAttribute("member") MemberDto memberDto, BindingResult bindingResult) {
        memberDto = memberService.getLoginMember(authentication);
        if (!memberService.confirmPassword(currentPw, memberDto.getPassword())) {
            model.addAttribute("message","비밀번호가 일치하지 않습니다.");
            return "password";
        } else if (bindingResult.hasErrors()) {
            return "password";
        }
        memberService.updatePassword(currentPw, password, id);
        return "redirect:/login";
    }

    // 계정 조회 페이지
    @GetMapping("/list")
    public String memberList (Model model, Authentication authentication, Criteria criteria, Integer levelSeq) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto);
        String name = null;
        if (memberDto.getLevelSeq() == 3) {
            name = memberDto.getName();
        } else if (memberDto.getLevelSeq() == 4) {
            name = memberDto.getClient();
        }
        if (memberDto.getLevelSeq() == 1 || memberDto.getLevelSeq() == 2) {
            model.addAttribute("list", memberService.getListByAdmin(criteria, levelSeq));
            model.addAttribute("pageMaker", new PageDto(memberService.getTotalAdminList(criteria, levelSeq), 10, criteria));
        } else if (memberDto.getLevelSeq() == 3 || memberDto.getLevelSeq() == 4) {
            model.addAttribute("list", memberService.getListByClient(criteria, name));
            model.addAttribute("pageMaker", new PageDto(memberService.getTotalClientList(criteria, name), 10, criteria));
        }
        return "member/list";
    }

    @GetMapping("/list/{levelSeq}") // 쿼리문으로 변경
    public String memberListUsingFilter(Model model, Authentication authentication, Criteria criteria, @PathVariable("levelSeq") Integer levelSeq) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto);
        model.addAttribute("list", memberService.getListByAdmin(criteria, levelSeq));
        model.addAttribute("pageMaker", new PageDto(memberService.getTotalAdminList(criteria, levelSeq), 10, criteria));
        return "member/list";
    }

    @GetMapping("/get/{id}")
    public String get(@PathVariable("id") String id, Model model, Authentication authentication, Criteria criteria) {
        MemberDto memberDto = memberService.getLoginMember(authentication);
        model.addAttribute("info", memberDto);
        model.addAttribute("member", memberService.getMemberById(id));
        model.addAttribute("criteria", criteria.getListLink(criteria.getPageNum()));
        return "member/get";
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
