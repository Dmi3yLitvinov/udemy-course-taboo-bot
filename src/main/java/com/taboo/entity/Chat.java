package com.taboo.entity;

import com.taboo.entity.enums.ChatBotStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long telegramChatId;
    @Enumerated(EnumType.STRING)
    private ChatBotStatus chatBotStatus;
}
