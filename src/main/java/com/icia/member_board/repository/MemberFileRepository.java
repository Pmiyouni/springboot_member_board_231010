package com.icia.member_board.repository;

import com.icia.member_board.entity.MemberFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberFileRepository extends JpaRepository<MemberFileEntity, Long> {
}
