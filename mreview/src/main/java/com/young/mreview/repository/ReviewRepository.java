package com.young.mreview.repository;

import com.young.mreview.entity.Movie;
import com.young.mreview.entity.Review;
import com.young.mreview.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 엔터티의 특정 속성을 같이 로딩하도록 하는 에너테이션
    // FETCH로 설정할 시, user 속성은 EAGER, 나머지는 LAZY로 처리함
    // LOAD로 설정시, 명시한 속성은 EAGER로 처리, 나머지는 엔터티 클래스에 명시되거나 기본 방식으로 처리함
    @EntityGraph(attributePaths = {"user"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

    // 리뷰 테이블에서 하나씩 삭제하는 비효율 작업을 방지하고자 where절을 지정함
    @Modifying
    @Query("delete from Review mr where mr.user = :user")
    void deleteByUser(User user);
}
