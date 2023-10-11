package com.taboo.entity;

import com.taboo.entity.enums.WaitRoomStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class WaitRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer messageId;
    private String hash;
    @Enumerated(EnumType.STRING)
    private WaitRoomStatus status = WaitRoomStatus.AWAITING;

    @ManyToOne
    private Chat chat;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "wait_room_user",
            joinColumns = @JoinColumn(name = "wait_room_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();
}
