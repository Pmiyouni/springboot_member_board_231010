package com.icia.member_board.entity;


import com.icia.member_board.dto.BoardDTO;
import com.icia.member_board.dto.FavoriteDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "favorite_table")
public class FavoriteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(columnDefinition = "int default 0")
//    private int fcnt;
//
//    @Column(columnDefinition = "int default 0")
//    private int ncnt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static FavoriteEntity toFavoriteEntity(MemberEntity memberEntity, BoardEntity boardEntity) {
        FavoriteEntity favoriteEntity = new FavoriteEntity();
        favoriteEntity.setMemberEntity(memberEntity);
        favoriteEntity.setBoardEntity(boardEntity);
//        favoriteEntity.setFcnt(fcnt);
//        favoriteEntity.setNcnt(ncnt);
        return favoriteEntity;
    }


}
