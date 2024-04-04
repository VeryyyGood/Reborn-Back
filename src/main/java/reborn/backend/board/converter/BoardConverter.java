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
                .boardType(board.getBoardType())
                .boardWriter(user.getUsername()) // 작성자 username으로 저장
                .likeCount(0L)
                .boardContent(board.getBoardContent())
                .imageAttached(board.getImageAttached())
                .boardImage(board.getBoardImage())
                .build();
    }

    public static BoardResDto simpleBoardDto(Board board) {
        return BoardResDto.builder()
                .id(board.getId())
                .boardType(board.getBoardType())
                .boardWriter(board.getBoardWriter()) // 작성자 username으로 저장
                .likeCount(board.getLikeCount())
                .boardContent(board.getBoardContent())
                .imageAttached(board.getImageAttached())
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
