package com.taboo.telegram.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.File;
import java.util.List;

@Component
public class MessageBuilder {

    public SendMessage buildTextMessage(Long chatId, String text) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }

    public SendMessage buildFormattedTextMessage(Long chatId) {
        var message = new SendMessage();
        message.setChatId(chatId);
        String parseMode = ParseMode.MARKDOWNV2;
        message.setParseMode(parseMode);
        message.setText(generateFormattedText(parseMode));
        return message;
    }
    public SendMessage buildReplyKeyboardMessage(Long chatId) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText("What game do you prefer?");
        var replyKeyboard = new ReplyKeyboardMarkup();
        var row1 = new KeyboardRow();
        row1.add("dice");
        row1.add("darts");
        var row2 = new KeyboardRow();
        row2.add("football");
        row2.add("basketball");
        var row3 = new KeyboardRow();
        row3.add("bowling");
        row3.add("casino");
        replyKeyboard.setKeyboard(List.of(row1, row2, row3));
        replyKeyboard.setResizeKeyboard(true);
        replyKeyboard.setOneTimeKeyboard(false);
        message.setReplyMarkup(replyKeyboard);
        return message;
    }

    public SendDice buildSendDice(Long chatId, String emoji) {
        var dice = new SendDice();
        dice.setChatId(chatId);
        dice.setEmoji(emoji);
        return dice;
    }

    public SendMessage buildDeleteKeyboardMessage(Long chatId) {
        var message = new SendMessage();
        message.setText("Come back soon!");
        message.setChatId(chatId);
        ReplyKeyboardRemove replyKeyboard = new ReplyKeyboardRemove();
        replyKeyboard.setRemoveKeyboard(true);
        message.setReplyMarkup(replyKeyboard);
        return message;
    }

    public SendPhoto buildPhotoMessage(Long chatId, String url, String caption) {
        var message = new SendPhoto();
        message.setChatId(chatId);
        message.setCaption(caption);
        var file = new InputFile(url);
        message.setPhoto(file);
        return message;
    }

    public SendDocument buildDocumentMessage(Long chatId, String caption, File file) {
        var message = new SendDocument();
        message.setChatId(chatId);
        message.setCaption(caption);
        var document = new InputFile(file, file.getName());
        message.setDocument(document);
        return message;
    }

    public SendSticker buildStickerMessage(Long chatId, String fileId) {
        var message = new SendSticker();
        message.setChatId(chatId);
        var file = new InputFile(fileId);
        message.setSticker(file);
        return message;
    }

    public String generateFormattedText(String parseMode) {
        if (ParseMode.MARKDOWNV2.equals(parseMode)) {
            return """
                    [inline URL](http://www.example.com/)
                    [inline user mention](tg://user?id=1234567890)
                    """;
        } else if (ParseMode.HTML.equals(parseMode)) {
            return """
                    <span class=tg-spoiler>The butler did it!</span>
                    <tg-spoiler>The butler did it!</tg-spoiler>
                    """;
        }
        return null;
    }

}
