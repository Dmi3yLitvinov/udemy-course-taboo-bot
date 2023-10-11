package com.taboo.repository;

import com.taboo.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {

    @Query("SELECT c.id FROM Card c")
    List<Long> getAllIds();
}
