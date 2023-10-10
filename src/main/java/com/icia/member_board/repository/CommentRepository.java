package com.icia.member_board.repository;

import com.icia.member_board.entity.BoardEntity;
import com.icia.member_board.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}