package com.icia.member_board.service;

import com.icia.member_board.dto.MemberDTO;
import com.icia.member_board.entity.MemberEntity;
import com.icia.member_board.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) throws IOException {
        if(memberDTO.getMemberProfile().isEmpty()){
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            return memberRepository.save(memberEntity).getId();
        } else{
        MultipartFile memberFile = memberDTO.getMemberProfile();
            // DTO에 담긴 파일 꺼내기
        String originalFilename = memberFile.getOriginalFilename();
        // 저장용 파일 이름
        String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
        // 저장경로+파일이름 준비
        String savePath = "D:\\springboot_img\\" + storedFileName;
        // 파일 폴더에 저장
        memberFile.transferTo(new File(savePath));
        MemberEntity memberEntity = MemberEntity.toSaveEntityWithFile(memberDTO);
        return memberRepository.save(memberEntity).getId();
    }
    }

    public MemberEntity login(MemberDTO memberDTO) {
        //  email, password 둘다 만족하는 조회결과가 있다면 로그인성공, 없다면 로그인실패
        Optional<MemberEntity> optionalMemberEntity =
                memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            return memberEntity;
        } else {
            return null;
        }
    }


    public boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

}
