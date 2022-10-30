package com.zhfvkq.dyshop.member.repository;

import com.zhfvkq.dyshop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

    boolean existsByUserId(String userId);
    Optional<Member> findByUserId(String userChkId);

}
