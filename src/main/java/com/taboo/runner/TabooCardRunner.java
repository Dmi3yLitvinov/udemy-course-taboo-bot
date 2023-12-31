package com.taboo.runner;

import com.taboo.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

@Order(2)
@Component
@RequiredArgsConstructor
public class TabooCardRunner implements ApplicationRunner {
    private final CardService cardService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (cardService.isTableEmpty()) {
            ClassPathResource resource = new ClassPathResource("/files/taboo-cards.txt");
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    List<String> answerAndTaboos = Arrays.stream(line.split(",")).toList();
                    cardService.save(answerAndTaboos);
                }
            }
        }
    }
}
