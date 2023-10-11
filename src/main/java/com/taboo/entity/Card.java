package com.taboo.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@TypeDefs({@TypeDef(name = "list-array", typeClass = ListArrayType.class)})
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String answer;
    @Type(type = "list-array")
    private List<String> taboos = new ArrayList<>();
    @Type(type = "list-array")
    private List<String> allTaboos = new ArrayList<>();
}
