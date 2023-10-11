package com.taboo.entity;

import com.taboo.entity.enums.CardStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class GameCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Card card;
    @ManyToOne
    private Game game;
    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.IN_PROGRESS;

}
