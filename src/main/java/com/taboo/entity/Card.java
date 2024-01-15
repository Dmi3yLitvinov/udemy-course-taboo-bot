package com.taboo.entity;

import com.taboo.entity.converter.StringListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;
    @Convert(converter = StringListConverter.class)
    private List<String> taboos = new ArrayList<>();
    @Convert(converter = StringListConverter.class)
    private List<String> allTaboos = new ArrayList<>();
}
