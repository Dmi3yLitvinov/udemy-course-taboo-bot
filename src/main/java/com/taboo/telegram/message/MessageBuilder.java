package com.taboo.telegram.message;

import com.taboo.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
public class MessageBuilder {

    public SendMessage buildWelcomeMsg(Long chatId, User user) {
        SendMessage message = new SendMessage();
        String txt = """
        Welcome %s! \uD83D\uDC4B
        This bot lets you play 🎮 the Taboo game with your friends.
        Here's how:
        1. Add this bot to a chat with other players.
        2. In that chat, send the /play command to start a new game.
        
        Use the /rules command to view the game rules
        """.formatted(user.getFirstName());
        message.setText(txt);
        message.setChatId(chatId);
        return message;
    }

    public SendMessage buildCommandNotFoundMsg(Message message){
        var sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText("You tried to perform an unknown command!");
        return sendMessage;
    }

    public SendMessage buildRulesMsg(Long chatId) {
        SendMessage message = new SendMessage();
        String txt = """
                There are two roles: \uD83D\uDC42Guesser and \uD83D\uDDE3 Explainer.
                
                The Explainer receives a card in a private chat that includes a word for others to guess and a list of taboo words \uD83D\uDEAB
                ✅ The objective is to assist other players in guessing the word while avoiding the use of any of the taboo words
                
                If any of the Guessers correctly guess a word, both the Explainer and Guesser earn a score\uD83C\uDFAF
                
                The Explainer role changes every five minutes ⌛, and the game continues until everyone has taken a turn explaining.       
                """;
        message.setText(txt);
        message.setChatId(chatId);
        return message;
    }

    public SendMessage buildTextMsg(Long chatId, String text) {
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        return message;
    }
}