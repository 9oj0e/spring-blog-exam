package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import shop.mtcoding.blog._core.Constant;
import shop.mtcoding.blog._core.PagingUtil;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final HttpSession session;
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String index(@RequestParam(defaultValue = "0") int page, HttpServletRequest request) {
        // 모델 조회
        List<Board> boardList = boardRepository.findAll(page);
        // 상자 담기
        session.setAttribute("boardList", boardList);

        // 페이징 todo 축약?
        request.setAttribute("nextPage", PagingUtil.nextPage(page));
        request.setAttribute("prevPage", PagingUtil.prevPage(page));

        int totalCount = boardRepository.count();
        session.setAttribute("first", PagingUtil.isFisrt(page));
        session.setAttribute("last", PagingUtil.isLast(page, totalCount));

        /* 게시글이 하나도 없을 때, next, prev 비활성화
        if (totalCount != 0) {
            first = PagingUtil.isFisrt(page);
            last = PagingUtil.isLast(page, totalCount);
        } else {
            first = true;
            last = true;
        }
        */

        // 페이징 넘버링
        int totalPageCount = PagingUtil.getTotalPageCount(totalCount);
        List<Integer> pageCount = PagingUtil.getPageList(totalPageCount);
        session.setAttribute("pageCount", pageCount);

        // todo 현재 페이지와 버튼 페이지가 같으면 버튼 비활성화
        //  if 현재 페이지 == 버튼 페이지 ? disabled : NULL

        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }

    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO, HttpServletRequest request) {
        System.out.println("게시글 삽입 시도..");
        // 유효성 검사
        if (requestDTO.getTitle().length() > 20 || requestDTO.getContent().length() > 20) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "제목과 내용은 20자를 초과할 수 없습니다.");
            return "error/40x";
        }
        if (requestDTO.getAuthor() == null) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "작성자의 이름이 누락되었습니다.");
        }
        // 모델 위임
        int result = boardRepository.insert(requestDTO);
        System.out.println("영향 받은 행: " + result);
        return "redirect:/";
    }

    @GetMapping("/board/{id}/updateForm")
    public String updateForm(@PathVariable int id) {
        // 모델 조회
        Board board = boardRepository.findById(id);
        // 상자 담기
        session.setAttribute("board", board);
        return "board/updateForm";
    }

    @PostMapping("/board/{id}/update")
    public String update(@PathVariable int id, BoardRequest.UpdateDTO requestDTO, HttpServletRequest request) {
        System.out.println("게시글 수정 시도..");
        // 유효성 검사
        if (requestDTO.getTitle().length() > 20 || requestDTO.getContent().length() > 20) {
            request.setAttribute("status", 400);
            request.setAttribute("msg", "제목과 내용은 20자를 초과할 수 없습니다.");
            return "error/40x";
        }
        /* 유효성 검사 (작성자) 추가
        if ( requestDTO.getAuthor() == null ){
            request.setAttribute("status", 400);
            request.setAttribute("msg", "작성자의 이름이 누락되었습니다.");
        }
        */
        // 모델 위임
        int result = boardRepository.updateById(requestDTO, id);
        System.out.println("영향 받은 행: " + result);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id) {
        System.out.println("게시글 삭제 시도..");
        // 모델 위임
        int result = boardRepository.deleteById(id);
        System.out.println("영향 받은 행: " + result);
        return "redirect:/";
    }
}
