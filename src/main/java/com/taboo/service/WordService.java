package com.taboo.service;

import com.taboo.entity.Word;
import com.taboo.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordRepository repository;

    public boolean isTableEmpty() {
        return repository.count() == 0;
    }

    public Word save(String word) {
        return repository.save(build(word));
    }

    public Word save(String word, Word initWord) {
        Word entity = build(word);
        entity.setInitWord(initWord);
        return repository.save(entity);
    }

    private Word build(String word) {
        var entity = new Word();
        entity.setWord(word);
        return entity;
    }
}
