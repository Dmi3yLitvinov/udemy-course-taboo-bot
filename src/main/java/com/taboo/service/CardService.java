package com.taboo.service;

import com.taboo.entity.Card;
import com.taboo.entity.Game;
import com.taboo.entity.GameCard;
import com.taboo.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardService {
    private final static Random NEXT_CARD_ID = new Random();

    private final CardRepository repository;
    private final WordService wordService;

    public Card save(List<String> answerAndTaboos) {
        var card = new Card();
        card.setAnswer(answerAndTaboos.get(0));
        card.setTaboos(answerAndTaboos.subList(1, answerAndTaboos.size()));
        card.setAllTaboos(
                answerAndTaboos.stream()
                        .flatMap(w -> wordService.getAllWordForms(w).stream())
                        .toList()
        );
        return repository.save(card);
    }

    public boolean isTableEmpty() {
        return repository.count() == 0;
    }

    public Card getNextCard(Game game) {
        Set<Long> usedCardIds = getCardIds(game);
        List<Long> allIds = repository.getAllIds();
        Long nextCardId;
        do {
            int index = NEXT_CARD_ID.nextInt(allIds.size());
            nextCardId = allIds.get(index);
        } while (usedCardIds.contains(nextCardId));

        return repository.findById(nextCardId).get();
    }

    private Set<Long> getCardIds(Game game) {
        return game.getCards().stream()
                .map(GameCard::getCard)
                .map(Card::getId)
                .collect(Collectors.toSet());
    }
}
