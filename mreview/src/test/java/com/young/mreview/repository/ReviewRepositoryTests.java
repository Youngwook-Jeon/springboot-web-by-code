package com.young.mreview.repository;

import com.young.mreview.entity.Movie;
import com.young.mreview.entity.Review;
import com.young.mreview.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void insertMovieReviews() {
        IntStream.rangeClosed(1, 200).forEach(i -> {
            Long mno = (long)(Math.random() * 100) + 1;
            Long mid = (long)(Math.random() * 100) + 1;
            User user = User.builder().mid(mid).build();

            Review movieReview = Review.builder()
                    .user(user)
                    .movie(Movie.builder().mno(mno).build())
                    .grade((int)(Math.random() * 5) + 1)
                    .text("이 영화에 대한 느낌..." + i)
                    .build();

            reviewRepository.save(movieReview);
        });
    }

    @Test
    public void testGetMovieReviews() {
        Movie movie = Movie.builder().mno(5L).build();
        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(movieReview -> {
            System.out.println(movieReview.getReviewnum());
            System.out.println("\t" + movieReview.getGrade());
            System.out.println("\t" + movieReview.getText());
            System.out.println("\t" + movieReview.getUser().getEmail());
            System.out.println("----------------------");
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteUser() {
        Long mid = 3L;
        User user = User.builder().mid(mid).build();

        // FK가 걸려있으므로 순서 주의
        reviewRepository.deleteByUser(user);
        userRepository.deleteById(mid);
    }
}
