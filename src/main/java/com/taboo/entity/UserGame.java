package com.taboo.entity;

import com.taboo.entity.enums.GameRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Game game;

    @Enumerated(EnumType.STRING)
    private GameRole gameRole = GameRole.GUESSER;

    private int score;
    private boolean explained;
}
