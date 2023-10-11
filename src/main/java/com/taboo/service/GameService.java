package com.taboo.service;

import com.taboo.entity.*;
import com.taboo.entity.enums.GameRole;
import com.taboo.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository repository;
    private final CardService cardService;

    public Game save(WaitRoom waitRoom) {
        var game = new Game();
        game.setChat(waitRoom.getChat());
        waitRoom.getUsers().stream()
                .map(this::buildUserGame)
                .forEach(game::addUserGame);
        assignExplainer(game);
        return repository.save(game);
    }

    public Card getNextCard(Game game) {
        Card nextCard = cardService.getNextCard(game);
        var gameCard = new GameCard();
        gameCard.setCard(nextCard);
        game.addGameCard(gameCard);
        return nextCard;
    }

    public User assignExplainer(Game game) {
        UserGame nextExplainer = game.getUsers().stream()
                .filter(ug -> !ug.isExplained())
                .findFirst()
                .orElse(null);
        if (nextExplainer != null) {
            nextExplainer.setGameRole(GameRole.EXPLAINER);
            return nextExplainer.getUser();
        }
        return null;
    }

    private UserGame buildUserGame(User user) {
        var userGame = new UserGame();
        userGame.setUser(user);
        return userGame;
    }
}
