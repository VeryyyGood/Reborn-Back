package reborn.backend.board.converter;

import lombok.NoArgsConstructor;
import org.hibernate.sql.ast.tree.update.Assignment;
import org.springframework.data.domain.Page;
import reborn.backend.board.domain.Board;
import reborn.backend.board.dto.BoardRequestDto;
import reborn.backend.board.dto.BoardRequestDto.BoardReqDto;
import reborn.backend.board.dto.BoardResponseDto.BoardListResDto;
import reborn.backend.board.dto.BoardResponseDto.BoardResDto;
import reborn.backend.user.domain.User;

import java.util.List;

@NoArgsConstructor
public class BoardConverter {
    public static Board saveBoard(BoardReqDto board, User user){
        return Board.builder()
                .user(user)
                .boardType(board.getBoardType())
                .boardWriter(user.getNickname()) // username으로 저장
                .likeCount(0L)
                .commentCount(0L)
                .boardContent(board.getBoardContent())
                .boardImage("null")
                .build();
    }

    public static BoardResDto simpleBoardDto(Board board) {
        return BoardResDto.builder()
                .id(board.getId())
                .boardType(board.getBoardType())
                .boardWriter(board.getBoardWriter()) // nickname으로 저장
                .writerProfileImage(board.getUser().getProfileImage())
                .likeCount(board.getLikeCount())
                .commentCount(board.getCommentCount())
                .boardContent(board.getBoardContent())
                .boardCreatedAt(board.getCreatedAt())
                .boardImage(board.getBoardImage())
                .build();
    }

    public static BoardListResDto boardListResDto(List<Board> boards) {
        List<BoardResDto> boardDtos
                = boards.stream().map(BoardConverter::simpleBoardDto).toList();

        return BoardListResDto.builder()
                .boardList(boardDtos)
                .build();
    }


}
