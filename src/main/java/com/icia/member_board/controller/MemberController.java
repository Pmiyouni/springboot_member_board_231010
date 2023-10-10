package com.icia.member_board.controller;

import com.icia.member_board.dto.MemberDTO;
import com.icia.member_board.entity.MemberEntity;
import com.icia.member_board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
   private  final MemberService memberService;

    @GetMapping("/save")
    public String save() {
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) throws IOException {
        memberService.save(memberDTO);
        return "memberPages/memberLogin";
    }

    @PostMapping("/dup-check")
    public ResponseEntity emailCheck(@RequestBody MemberDTO memberDTO) {
        boolean result = memberService.emailCheck(memberDTO.getMemberEmail());
        if (result) {
            return new ResponseEntity<>("사용가능", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/login")
    public String login() {
        //model.addAttribute("redirectURI", redirectURI);
        return "memberPages/memberLogin";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session,Model model){
        MemberEntity memberEntity = memberService.login(memberDTO);
        boolean error= false;
        if (memberEntity != null) {
            session.setAttribute("loginEmail", memberEntity.getMemberEmail());
            session.setAttribute("memberId", memberEntity.getId());
           // model.addAttribute("email", memberEntity.getMemberEmail());
            return "boardPages/boardList";
        } else {
            System.out.println("사용자 정보가 없습니다");
            error = true;
            model.addAttribute("error",error);
            return "memberPages/memberLogin";
        }
    }

        @GetMapping("/logout")
        public String logout(HttpSession session) {
            session.removeAttribute("loginEmail");
            session.removeAttribute("memberId");
            // 세션 전체를 없앨 경우
//        session.invalidate();
            return "redirect:/";
        }
    @GetMapping("/admin")
    public String admin() {
        return "memberPages/admin";
    }
}
