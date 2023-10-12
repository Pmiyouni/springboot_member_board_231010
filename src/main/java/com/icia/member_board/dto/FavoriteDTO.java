package com.icia.member_board.dto;

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
    private int fcnt=0; //좋아요수
    private int ncnt=0; //싫어요수
}
