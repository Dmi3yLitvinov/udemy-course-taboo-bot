package com.taboo.service;

import com.taboo.entity.*;
import com.taboo.entity.enums.CardStatus;
import com.taboo.entity.enums.GameRole;
import com.taboo.entity.enums.GameStatus;
import com.taboo.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.taboo.entity.enums.CardStatus.COMPLETED;
import static com.taboo.entity.enums.CardStatus.IN_PROGRESS;

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

    public Game findGameInProgress(Long telegramChatId) {
        return repository.findByChat_telegramChatIdAndStatus(telegramChatId, GameStatus.IN_PROGRESS);
    }

    public GameCard getGameCardInProgress(Game game) {
        return game.getCards().stream()
                .filter(gc -> gc.getStatus().equals(IN_PROGRESS))
                .findFirst()
                .orElseThrow();
    }

    public UserGame whoseMessage(Long telegramId, Game game) {
        return game.getUsers().stream()
                .filter(ug -> ug.getUser().getTelegramId().equals(telegramId))
                .findFirst()
                .orElseThrow();
    }

    public Set<String> findTabooUsed(Card card, String text) {
        return extractWords(text.toLowerCase()).stream()
                .filter(w -> card.getAllTaboos().contains(w))
                .collect(Collectors.toSet());
    }

    private List<String> extractWords(String text) {
        return Arrays.stream(text.split("[^a-zA-Z-]+")).toList();
    }

    public boolean isGuessed(Card card, String text) {
        return extractWords(text.toLowerCase()).contains(card.getAnswer());
    }

    public void cardWasGuessed(GameCard gameCard, UserGame guesser) {
        guesser.setScore(guesser.getScore() + 1);
        Game game = gameCard.getGame();
        UserGame explainer = getUserGameExplainer(game);
        explainer.setScore(explainer.getScore() + 1);
        gameCard.setStatus(COMPLETED);
    }

    public UserGame getUserGameExplainer(Game game) {
        return game.getUsers().stream()
                .filter(ug -> ug.getGameRole() == GameRole.EXPLAINER)
                .findFirst()
                .orElseThrow();
    }

    public User getExplainer(Game game) {
        return game.getUsers().stream()
                .filter(ug -> ug.getGameRole() == GameRole.EXPLAINER)
                .findFirst()
                .map(UserGame::getUser)
                .orElseThrow();
    }

    public Game timeIsUp(Long telegramChatId) {
        Game game = findGameInProgress(telegramChatId);
        GameCard gameCard = getGameCardInProgress(game);
        gameCard.setStatus(CardStatus.TIME_IS_UP);
        UserGame userGame = getUserGameExplainer(game);
        userGame.setGameRole(GameRole.GUESSER);
        userGame.setExplained(true);
        return game;
    }
}
