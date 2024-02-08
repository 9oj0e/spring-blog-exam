package shop.mtcoding.blog.board;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final HttpSession session;
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String index() {
        // 모델 위임
        List<Board> boardList = boardRepository.findAll();
        // 상자 담기
        session.setAttribute("boardList", boardList);
        return "index";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
    @PostMapping("/board/save")
    public String save(BoardRequest.SaveDTO requestDTO){
        System.out.println("게시글 삽입 시도..");
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
    public String update(@PathVariable int id, BoardRequest.UpdateDTO requestDTO){
        System.out.println("게시글 수정 시도..");
        // 모델 위임
        int result = boardRepository.updateById(requestDTO, id);
        System.out.println("영향 받은 행: " + result);
        return "redirect:/";
    }

    @PostMapping("/board/{id}/delete")
    public String delete(@PathVariable int id){
        System.out.println("게시글 삭제 시도..");
        // 모델 위임
        int result = boardRepository.deleteById(id);
        System.out.println("영향 받은 행: " + result);
        return "redirect:/";
    }
}
