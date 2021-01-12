package com.young.club.repository;

import com.young.club.entity.ClubUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClubUserRepository extends JpaRepository<ClubUser, String> {

    // ClubUserRole과 left outer join 처리
    @EntityGraph(attributePaths = {"roleSet"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from ClubUser u where u.fromSocial = :social and u.email = :email")
    Optional<ClubUser> findByEmail(String email, boolean social);
}
