package com.icia.member_board.repository;

import com.icia.member_board.entity.BoardEntity;
import com.icia.member_board.entity.FavoriteEntity;
import com.icia.member_board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Long> {

  //Optional<FavoriteEntity> findByMemberEntityAndCommentEntity(MemberEntity memberEntity, CommentEntity commentEntity);
  //FavoriteEntity findByMemberEntityAndBoardEntity(MemberEntity memberEntity, BoardEntity boardEntity);
  //void deleteByMemberEntityAndCommentEntity(MemberEntity memberEntity, CommentEntity commentEntity);



  Optional<FavoriteEntity> findByBoardEntity(BoardEntity boardEntity);

  Optional<FavoriteEntity> findByMemberEntityAndBoardEntity(MemberEntity memberEntity, BoardEntity boardEntity);
}
