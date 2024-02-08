package shop.mtcoding.blog.board;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "board_tb")
@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 20, nullable = false)
    private String title;
    @Column(length = 20)
    private String content;
    @Column(length = 10, nullable = false)
    private String author;
    @CreationTimestamp
    private LocalDateTime createdAt;
}
