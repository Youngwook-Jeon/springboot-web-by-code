package com.young.mreview.repository;

import com.young.mreview.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

//    N + 1 문제를 야기하는 상황: 10개의 데이터를 가져오는 쿼리 + 각 영화의 모든 이미지를 가져오기위한 10번의 추가 쿼리 실행
//    @Query("select m, max(mi), avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
//            "left outer join MovieImage mi on mi.movie = m " +
//            "left outer join Review r on r.movie = m group by m")
//    Page<Object[]> getListPage(Pageable pageable);

    // MySQL에서 ONLY_FULL_GROUP_BY 모드를 해제하기 위해 db url에서 sessionVariables=sql_mode='' 추가함
    // 이미지를 1개로 줄여서 처리
    @Query(value = "select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m " +
            "left outer join MovieImage mi on mi.movie = m " +
            "left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(r)" +
            " from Movie m left outer join MovieImage mi on mi.movie = m " +
            " left outer join Review r on r.movie = m " +
            " where m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(Long mno);
}
