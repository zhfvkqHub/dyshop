package com.zhfvkq.dyshop.member.repository;

import com.zhfvkq.dyshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsById(String userId);

    @Override
    Optional<Member> findById(String userChkId);
}
