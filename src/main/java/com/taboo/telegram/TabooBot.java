package com.taboo.telegram;

import com.taboo.telegram.message.MessageBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

@Slf4j
@Component
public class TabooBot extends TelegramLongPollingBot {

    private final String username;
    private final MessageBuilder messageBuilder;
    private static final String NEW_YORK_PHOTO_URL = "https://media.gq-magazine.co.uk/photos/5d13a9c2976fa37177f3b040/4:3/w_1704,h_1278,c_limit/hp-gq-6dec18_istock_.jpg";
    private static final String NEW_YORK_PHOTO_CAPTION = "New-York city";
    private static final String STICKER_FILE_ID = "CAACAgIAAxkBAAM6ZNjSGqVijTiJ9wyBC8qPeqsrUj8AAv4AA1advQraBGEwLvnX_zAE";


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
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        if (message.hasText()) {
            String text = message.getText();
            switch (text) {
                case "text" -> execute(messageBuilder.buildTextMessage(chatId, "My text message"));
                case "photo" -> {
                    var sendPhoto = messageBuilder.buildPhotoMessage(chatId, NEW_YORK_PHOTO_URL, NEW_YORK_PHOTO_CAPTION);
                    execute(sendPhoto);
                }
                case "document" -> {
                    var sendDocument = messageBuilder.buildDocumentMessage(chatId, "Here is a file", readFile("/files/New-York.jpeg"));
                    execute(sendDocument);
                }
                case "sticker" -> {
                    var sendSticker = messageBuilder.buildStickerMessage(chatId, STICKER_FILE_ID);
                    execute(sendSticker);
                }
            }
        } else if (message.hasSticker()) {
            log.info("Sticker file id={}", message.getSticker().getFileId());
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
}
