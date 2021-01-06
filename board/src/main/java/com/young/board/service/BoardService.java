package com.young.board.service;

import com.young.board.dto.BoardDTO;
import com.young.board.dto.PageRequestDTO;
import com.young.board.dto.PageResultDTO;
import com.young.board.entity.Board;
import com.young.board.entity.User;

public interface BoardService {

    Long register(BoardDTO dto);

    PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno);

    void modify(BoardDTO boardDTO);

    default Board dtoToEntity(BoardDTO dto) {
        User user = User.builder().email(dto.getWriterEmail()).build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(user)
                .build();

        return board;
    }

    default BoardDTO entityToDTO(Board board, User user, Long replyCount) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(user.getEmail())
                .writerName(user.getName())
                .replyCount(replyCount.intValue()) // Long -> int
                .build();

        return boardDTO;
    }
}
