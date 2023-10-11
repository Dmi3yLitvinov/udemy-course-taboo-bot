package com.taboo.entity;

import com.taboo.entity.enums.GameStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GameStatus status = GameStatus.IN_PROGRESS;
    @ManyToOne
    private Chat chat;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<UserGame> users = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<GameCard> cards = new ArrayList<>();

    public void addUserGame(UserGame userGame) {
        userGame.setGame(this);
        users.add(userGame);
    }

    public void addGameCard(GameCard gameCard) {
        gameCard.setGame(this);
        cards.add(gameCard);
    }
}
