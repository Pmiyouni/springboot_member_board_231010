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
import java.util.List;
import java.util.NoSuchElementException;

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
            return "redirect:"+"/board";
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

    //my page
    @GetMapping("/mypage")
    public String updateForm(HttpSession session, Model model) {
        // 세션에 저장된 이메일 꺼내기
       String memberEmail = (String) session.getAttribute("loginEmail");
       MemberEntity memberEntity =  memberService.findByMemberEmail(memberEmail);
       MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
       model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
    }

    //회원정보수정
    @PostMapping("/update")
    public String update(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
           memberService.update(memberDTO);
       // session.removeAttribute("loginEmail");
        return "memberPages/memberDetail";
    }

    //회원수정
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody MemberDTO memberDTO, HttpSession session) {
        memberService.update(memberDTO);
        //session.removeAttribute("loginEmail");
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping
    public String findAll(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "memberPages/memberList";
    }

    @PostMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable("id") Long id) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            return new ResponseEntity<>(memberDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        try {
            MemberDTO memberDTO = memberService.findById(id);
            model.addAttribute("member", memberDTO);
            return "memberPages/memberDetail";
        } catch (NoSuchElementException e) {
            return "memberPages/NotFound";
        } catch (Exception e) {
            return "memberPages/NotFound";
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
