package com.young.board.service;

import com.young.board.dto.ReplyDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReplyServiceTests {

    @Autowired
    private ReplyService service;

    @Test
    public void testGetList() {
        Long bno = 100L;
        List<ReplyDTO> replyDTOList = service.getList(bno);
        replyDTOList.forEach(System.out::println);
    }
}
