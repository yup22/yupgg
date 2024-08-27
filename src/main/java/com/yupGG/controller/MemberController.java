package com.yupGG.controller;

import com.yupGG.dto.MemberFormDto;
import com.yupGG.entity.Member;
import com.yupGG.service.MailService;
import com.yupGG.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final MemberService memberService;
    private final MailService mailService;
    String confirm = "";
    boolean confirmCheck = false;
    @GetMapping("/login")
    public String Login() {

        return "members/login";
    }

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto",new MemberFormDto());
        return "members/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult,
                             Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            return "members/memberForm";
        }
        if(!confirmCheck){
            model.addAttribute("errorMessage","이메일 인증이 필요합니다.");
            return "members/memberForm";
        }

        if (!memberFormDto.getPassword().equals(memberFormDto.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "error.memberFormDto", "비밀번호가 일치하지 않습니다.");
            return "members/memberForm"; // 비밀번호가 일치하지 않으면 폼으로 돌아가기
        }
        try{
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        }
        catch (IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "members/memberForm";
        }

        redirectAttributes.addFlashAttribute("successMessage", "회원가입이 완료되었습니다!");
        return "redirect:/members/login";
    }

    @PostMapping("/{email}/emailConfirm")
    public @ResponseBody ResponseEntity emailConfirm(@PathVariable("email") String email)
            throws Exception{
        confirm = mailService.sendSimpleMessage(email);
        return new ResponseEntity<String>("인증 메일이 전송되었습니다.", HttpStatus.OK);
    }

    @PostMapping("/{code}/codeCheck")
    public @ResponseBody ResponseEntity codeConfirm(@PathVariable("code")String code)
            throws Exception{
        if(confirm.equals(code)){
            confirmCheck = true;
            return new ResponseEntity<String>("인증 성공하였습니다.",HttpStatus.OK);
        }
        return new ResponseEntity<String>("인증 코드를 올바르게 입력해주세요.",HttpStatus.BAD_REQUEST);
    }
}
