package com.taboo.telegram;

import com.taboo.telegram.message.MessageBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;
import java.util.Map;

import static com.taboo.telegram.message.MessageBuilder.DONATE_CALLBACK;

@Slf4j
@Component
public class TabooBot extends TelegramLongPollingBot {

    private final String username;
    private final MessageBuilder messageBuilder;
    private static final String NEW_YORK_PHOTO_URL = "https://media.gq-magazine.co.uk/photos/5d13a9c2976fa37177f3b040/4:3/w_1704,h_1278,c_limit/hp-gq-6dec18_istock_.jpg";
    private static final String NEW_YORK_PHOTO_CAPTION = "New-York city";
    private static final String STICKER_FILE_ID = "CAACAgIAAxkBAAM6ZNjSGqVijTiJ9wyBC8qPeqsrUj8AAv4AA1advQraBGEwLvnX_zAE";

    //    “🎲”, “🎯”, “🏀”, “⚽”, “🎳”, or “🎰”
    public static final Map<String, String> GAME_BY_EMOJI  = Map.of(
            "dice", "\uD83C\uDFB2",
            "darts", "\uD83C\uDFAF",
            "basketball", "\uD83C\uDFC0",
            "football", "⚽",
            "bowling", "\uD83C\uDFB3",
            "casino", "\uD83C\uDFB0"
    );

    public TabooBot(@Value("${app.telegram.username}") String username,
                    @Value("${app.telegram.token}") String botToken,
                    MessageBuilder messageBuilder) {
        super(botToken);
        this.username = username;
        this.messageBuilder = messageBuilder;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            log.info("telegram id ={}", message.getFrom().getId());
            if (message.hasText()) {
                if (message.hasEntities()) {
                    logMessageEntities(message);
                }
                String text = message.getText();
                if (GAME_BY_EMOJI.keySet().contains(text)) {
                    SendDice sendDice = messageBuilder.buildSendDice(chatId, GAME_BY_EMOJI.get(text));
                    execute(sendDice);
                    return;
                }
                switch (text) {
                    case "/text" -> execute(messageBuilder.buildTextMessage(chatId, "My text message"));
                    case "/photo" -> {
                        var sendPhoto = messageBuilder.buildPhotoMessage(chatId, NEW_YORK_PHOTO_URL, NEW_YORK_PHOTO_CAPTION);
                        execute(sendPhoto);
                    }
                    case "/document" -> {
                        var sendDocument = messageBuilder.buildDocumentMessage(chatId, "Here is a file", readFile("/files/New-York.jpeg"));
                        execute(sendDocument);
                    }
                    case "/sticker" -> {
                        var sendSticker = messageBuilder.buildStickerMessage(chatId, STICKER_FILE_ID);
                        execute(sendSticker);
                    }
                    case "/formattedText" -> {
                        var formattedMessage = messageBuilder.buildFormattedTextMessage(chatId);
                        execute(formattedMessage);
                    }
                    case "/play" -> {
                        var replyKeyboardMessage = messageBuilder.buildReplyKeyboardMessage(chatId);
                        execute(replyKeyboardMessage);
                    }
                    case "/endGame" -> {
                        execute(messageBuilder.buildDeleteKeyboardMessage(chatId));
                    }
                    case "/donate" -> {
                        execute(messageBuilder.buildInlineKeyboardMessage(chatId));
                    }
                }
            } else if (message.hasSticker()) {
                log.info("Sticker file id={}", message.getSticker().getFileId());
            }
        } else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            Long chatId = callbackQuery.getMessage().getChatId();
            if (DONATE_CALLBACK.equals(callbackQuery.getData())) {
                execute(messageBuilder.buildDonateOptions(chatId));
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @SneakyThrows
    private File readFile(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getFile();
    }

    public void logMessageEntities(Message message) {
        message.getEntities().forEach(entity -> {
            String logText =
                    switch(entity.getType()) {
                        case "text_link" -> "text=%s, link=%s".formatted(entity.getText(), entity.getUrl());
                        case "text_mention" -> "text=%s, userId=%s".formatted(entity.getText(), entity.getUser().getId());
                        default -> entity.getText();
                    };
            log.info("Message entity={}", logText);
        });
    }
}
