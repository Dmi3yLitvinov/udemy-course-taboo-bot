package com.taboo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String word;
    @ManyToOne
    @JoinColumn(name = "init_form_id")
    private Word initWord;
    @OneToMany(mappedBy = "initWord")
    private List<Word> forms = new ArrayList<>();

}
