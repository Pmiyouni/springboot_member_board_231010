package com.icia.member_board.repository;

import com.icia.member_board.entity.BoardEntity;
import com.icia.member_board.entity.FavoriteEntity;
import com.icia.member_board.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity,Long> {

//  Optional<FavoriteEntity> findByMemberEntityAndCommentEntity(MemberEntity memberEntity, CommentEntity commentEntity);
  List<FavoriteEntity> findByMemberEntityAndBoardEntity(MemberEntity memberEntity, BoardEntity boardEntity);
  //void deleteByMemberEntityAndCommentEntity(MemberEntity memberEntity, CommentEntity commentEntity);

  @Modifying
  @Query(value = "update FavoriteEntity f set f.fcnt=f.fcnt+1 where f.memberEntity.id=:id1 and f.boardEntity.id=:id2")
  int increaseLike(@Param("memberId") Long id1, @Param("boardId") Long id2);

}
