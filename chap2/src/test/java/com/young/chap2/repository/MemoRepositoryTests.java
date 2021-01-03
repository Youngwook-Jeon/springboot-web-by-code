package com.young.chap2.repository;

import com.young.chap2.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    // 의존성 주입에 문제가 없는지 먼저 테스트 해보기
    // 스프링 부트가 해당 인터페이스 타입의 클래스를 자동 생성하는데, 이때의 클래스 이름을 직접 확인해보기
    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." + i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect() {
        Long mno = 100L;
        // findById 메서드는 데이터베이스를 먼저 이용하는 방식
        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("===========================================");
        if (result.isPresent()) {
            System.out.println(result.get());
        }
    }

    @Test
    @Transactional
    public void testSelect2() {
        Long mno = 100L;
        // getOne은 필요한 순간까지 미룸. @Transactional 에너테이션이 추가로 필요함
        Memo memo = memoRepository.getOne(mno);
        System.out.println("===========================================");
        System.out.println(memo);
    }

    // Select 구문이 먼저 실행 -> 해당 엔터티 객체 있으면 update, 그렇지 않다면 insert 실행
    @Test
    public void testUpdate() {
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
    }

    // select -> delete
    @Test
    public void testDelete() {
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault() {
        Pageable pageable = PageRequest.of(0, 10);
        // 해당하는 페이지 정보만 가져오는 것이 아닌, 페이지 처리에 필요한 전체 데이터의 갯수를 가져오는 쿼리도 실행
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        System.out.println("Total Pages: " + result.getTotalPages());
        System.out.println("Total Count: " + result.getTotalElements());
        System.out.println("Page Size: " + result.getSize());
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Pageable pageable = PageRequest.of(0, 10, sort1);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(System.out::println);
    }

    @Test
    public void testQueryMethods() {
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);

        for (Memo memo: list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
        result.get().forEach(System.out::println);
    }

    @Test
    @Commit
    @Transactional
    public void testDeleteQueryMethod() {
        // deleteBy..는 각 엔터티 객체를 하나씩 삭제, 비효율적
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
