package shop.mtcoding.blog.board;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import shop.mtcoding.blog._core.Constant;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {
    private final EntityManager em;

    public List<Board> findAll() {
        Query query = em.createNativeQuery("select * from board_tb order by id desc", Board.class);

        return query.getResultList();
    }

    public List<Board> findAll(int page) { // 페이징
        int value = page * Constant.PAGING_COUNT;
        Query query = em.createNativeQuery("select * from board_tb order by id desc limit ?, ?", Board.class);
        query.setParameter(1, value);
        query.setParameter(2, Constant.PAGING_COUNT);

        return query.getResultList();
    }

    public int count() { // 페이징
        Query query = em.createNativeQuery("select count(*) from board_tb");

        return ((Number) query.getSingleResult()).intValue();
    }

    public Board findById(int id) {
        Query query = em.createNativeQuery("select * from board_tb where id = ?", Board.class);
        query.setParameter(1, id);

        return (Board) query.getSingleResult();
    }

    @Transactional
    public int insert(BoardRequest.SaveDTO requestDTO) {
        Query query = em.createNativeQuery("insert into board_tb(title, content, author, created_at) values (?, ?, ?, now())");
        query.setParameter(1, requestDTO.getTitle());
        query.setParameter(2, requestDTO.getContent());
        query.setParameter(3, requestDTO.getAuthor());

        return query.executeUpdate();
    }

    @Transactional
    public int updateById(BoardRequest.UpdateDTO requestDTO, int id) {
        Query query = em.createNativeQuery("update board_tb set content = ?, title = ? where id = ?");
        query.setParameter(1, requestDTO.getContent());
        query.setParameter(2, requestDTO.getTitle());
        /* 작성자 수정 희망시,
        query.setParameter(?, requestDTO.getAuthor());
        */
        query.setParameter(3, id);

        return query.executeUpdate();
    }

    @Transactional
    public int deleteById(int id) {
        Query query = em.createNativeQuery("delete from board_tb where id = ?");
        query.setParameter(1, id);

        return query.executeUpdate();
    }

}
