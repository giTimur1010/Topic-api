package ru.imanov.topictask.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.imanov.topictask.rest.dto.MessageWithoutTopicDto;
import ru.imanov.topictask.rest.dto.NewTopicRequestDto;
import ru.imanov.topictask.service.TopicService;

import java.time.ZonedDateTime;

@RestController
@RequestMapping("api/v1/setup")
@RequiredArgsConstructor
public class DbController {

    private final TopicService topicService;
    @PostMapping
    ResponseEntity<?> setUpDb() {

        for (int i = 0; i < 100; ++i) {
            topicService.add(
                    NewTopicRequestDto.builder()
                            .topicName("topic" + i)
                            .message(
                                MessageWithoutTopicDto.builder()
                                        .id("19211425-97d8-a4371-bc91-d1b11ca32" + i)
                                        .text("сообщение" + (i + 1))
                                        .author("Timur")
                                        .created(ZonedDateTime.now().toString())
                                        .build()
                            )
                            .build()
            );

        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
