package com.taboo.telegram.message;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;

import java.io.File;

@Component
public class MessageBuilder {

    public SendMessage buildTextMessage(Long chatId, String text) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
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

}
