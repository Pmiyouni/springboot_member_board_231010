package com.icia.member_board.dto;

import com.icia.member_board.entity.FavoriteEntity;
import com.icia.member_board.entity.MemberEntity;
import com.icia.member_board.util.UtilClass;
import lombok.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FavoriteDTO {
    private Long id;
    private Long memberId;
    private Long boardId;


    public static FavoriteDTO toDTO(FavoriteEntity favoriteEntity) {
        FavoriteDTO favoriteDTO = new FavoriteDTO();
        favoriteDTO.setId(favoriteEntity.getId());
        favoriteDTO.setMemberId(favoriteEntity.getMemberEntity().getId());
        favoriteDTO.setBoardId(favoriteEntity.getBoardEntity().getId());
        return favoriteDTO;
    }
}