package com.icia.member_board.dto;


import com.icia.member_board.entity.MemberEntity;
import com.icia.member_board.util.UtilClass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberBirth;
    private String memberMobile;
    private String createdAt;


    private MultipartFile memberProfile;
    private int fileAttached;
    private String originalFileName;
    private String storedFileName;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        memberDTO.setCreatedAt(UtilClass.dateTimeFormat(memberEntity.getCreatedAt()));

        if (memberEntity.getFileAttached() == 1) {
            memberDTO.setOriginalFileName(memberEntity.getOriginalFileName());
            memberDTO.setStoredFileName(memberEntity.getStoredFileName());
            memberDTO.setFileAttached(1);
        } else {
            memberDTO.setFileAttached(0);
        }
        return memberDTO;

    }
}
