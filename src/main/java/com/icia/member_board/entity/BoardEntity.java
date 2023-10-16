package com.icia.member_board.entity;


import com.icia.member_board.dto.BoardDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Column(length = 50, nullable = false)
    private String boardTitle;

    @Column(length = 20, nullable = false)
    private String boardPass;

    @Column(length = 500)
    private String boardContents;

    @Column(columnDefinition = "int default 0")
    private int boardHits;

    @Column
    private int fileAttached;


    @Column(columnDefinition = "int default 0")
    private int fcnt;




    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private  List<FavoriteEntity> favoriteEntity = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


    public static BoardEntity toSaveEntity(MemberEntity memberSaveEntity,BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setMemberEntity(memberSaveEntity);
        boardEntity.setFileAttached(0);
        return boardEntity;
    }

    public static BoardEntity toSaveEntityWithFile(MemberEntity memberSaveEntity,BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setMemberEntity(memberSaveEntity);
        boardEntity.setFileAttached(1);
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(MemberEntity memberSaveEntity,BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setFileAttached(boardDTO.getFileAttached());
        boardEntity.setMemberEntity(memberSaveEntity);
        //boardEntity.setFcnt(boardDTO.getFcnt());
       // boardEntity.setNcnt(boardDTO.getNcnt());
        return boardEntity;
    }

}