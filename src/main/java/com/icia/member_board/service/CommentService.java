package com.icia.member_board.service;


import com.icia.member_board.dto.CommentDTO;
import com.icia.member_board.entity.BoardEntity;
import com.icia.member_board.entity.CommentEntity;
import com.icia.member_board.entity.MemberEntity;
import com.icia.member_board.repository.BoardRepository;
import com.icia.member_board.repository.CommentRepository;
import com.icia.member_board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    public Long save(CommentDTO commentDTO,Long memberId1 ) {
        MemberEntity memberSaveEntity = memberRepository.findById(memberId1).orElseThrow(() -> new NoSuchElementException());
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.toSaveEntity(memberSaveEntity, boardEntity, commentDTO);
        return commentRepository.save(commentEntity).getId();
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());
        // 1. BoardEntity에서 댓글 목록 가져오기
//        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        // 2. CommentRepository에서 가져오기
        // select * from comment_table where board_id=?
        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntityOrderByIdDesc(boardEntity);
        //boardid를 직접 입력한 적이 없으므로 boardEntity로 쿼리문으로 수행(where board_id 대신)

        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(comment -> {
            commentDTOList.add(CommentDTO.toDTO(comment));
        });
        return commentDTOList;
    }

    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}