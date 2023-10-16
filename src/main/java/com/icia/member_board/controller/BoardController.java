package com.icia.member_board.controller;


import com.icia.member_board.dto.BoardDTO;
import com.icia.member_board.dto.CommentDTO;
import com.icia.member_board.dto.FavoriteDTO;
import com.icia.member_board.entity.FavoriteEntity;
import com.icia.member_board.service.BoardService;
import com.icia.member_board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String save() {
        return "boardPages/boardSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO, HttpSession session) throws IOException {
        Long memberId1 = (Long) session.getAttribute("memberId");
        System.out.println(memberId1);
        boardService.save(boardDTO, memberId1);
        return "redirect:/board";
    }

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
                          @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        Page<BoardDTO> boardDTOList = boardService.findAll(page, type, q);

        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardDTOList.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOList.getTotalPages();

        model.addAttribute("boardList", boardDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);

        return "boardPages/boardList";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model,HttpSession session,
                           @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                           @RequestParam(value = "type", required = false, defaultValue = "boardTitle") String type,
                           @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        boardService.increaseHits(id);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);

        //Long memberId = (Long) session.getAttribute("memberId");
        try {
            BoardDTO boardDTO = boardService.findById(id);
            model.addAttribute("board", boardDTO);
            System.out.println("boardDTO = " + boardDTO);

//            model.addAttribute("favorite", favoriteDTO);
//            System.out.println("favoriteDTO = " + favoriteDTO);
            List<CommentDTO> commentDTOList = commentService.findAll(id);
            if (commentDTOList.size() > 0) {
                model.addAttribute("commentList", commentDTOList);
            } else {
                model.addAttribute("commentList", null);
            }
            return "boardPages/boardDetail";
        } catch (NoSuchElementException e) {
            return "boardPages/boardNotFound";
        }
    }

    // 주소로 요청
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.delete(id);
        return "redirect:/board";
    }

    // axios로 delete 요청
    @DeleteMapping("/{id}")
    public ResponseEntity deleteByAxios(@PathVariable("id") Long id) {
        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "boardPages/boardUpdate";
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody BoardDTO boardDTO, HttpSession session) {
        Long memberId = (Long) session.getAttribute("memberId");
        boardService.update(boardDTO, memberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity clike(@RequestBody FavoriteDTO favoriteDTO) {
       boolean result = boardService.likeCheck(favoriteDTO);
        System.out.println("result = " + result);
        if (result) {
            FavoriteEntity favoriteEntity = boardService.findLikeById(favoriteDTO);
            FavoriteDTO favoriteDTO1 = FavoriteDTO.toDTO(favoriteEntity);
            int fcnt= boardService.slike(favoriteDTO1);
            System.out.println("fcnt = " + fcnt);
           
            return new ResponseEntity<>("사용가능", HttpStatus.OK);
        } else {
            System.out.println("fff");
            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
        }
    }

//    @PostMapping("/hate")
//    public ResponseEntity hate(@RequestBody FavoriteDTO favoriteDTO) {
//
//        boolean checkResult = boardService.likeCheck(favoriteDTO);
//        if (checkResult) {
//            Long id = boardService.like(favoriteDTO);
//            FavoriteDTO favoriteDTO1 = boardService.findLikeById(id);
//            return new ResponseEntity<>(favoriteDTO1, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("사용불가능", HttpStatus.CONFLICT);
//        }
//    }
}


