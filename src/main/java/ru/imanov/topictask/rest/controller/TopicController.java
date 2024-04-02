package ru.imanov.topictask.rest.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imanov.topictask.entity.Message;
import ru.imanov.topictask.entity.Topic;
import ru.imanov.topictask.repository.MessageRepository;
import ru.imanov.topictask.repository.TopicRepository;
import ru.imanov.topictask.rest.dto.MessageWithoutTopicDto;
import ru.imanov.topictask.rest.dto.NewTopicRequestDto;
import ru.imanov.topictask.rest.dto.TopicAllFieldsDto;
import ru.imanov.topictask.rest.dto.TopicWithoutMessagesDto;
import ru.imanov.topictask.service.TopicService;
import java.util.List;

@RequestMapping("api/v1/topic")
@RestController
@RequiredArgsConstructor
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    ResponseEntity<TopicAllFieldsDto> createTopic(@RequestBody NewTopicRequestDto request) {
        return ResponseEntity.ok(topicService.add(request));
    }

    @PutMapping
    ResponseEntity<TopicAllFieldsDto> updateTopic(@RequestBody TopicWithoutMessagesDto request) {
        return ResponseEntity.ok(topicService.update(request));
    }

    @GetMapping("/{offset}/{limit}")
    ResponseEntity<List<TopicWithoutMessagesDto>> getAllTopics(
            @PathVariable @Min(0) Integer offset,
            @PathVariable @Min(1) @Max(100) Integer limit
    ) {
        return ResponseEntity.ok(topicService.getAll(offset, limit));
    }

    @GetMapping("/{topic_id}")
    ResponseEntity<TopicAllFieldsDto> getTopicById(@PathVariable String topic_id) {
        return ResponseEntity.ok(topicService.getById(topic_id));
    }

    @PostMapping("/{topicId}/message")
    ResponseEntity<TopicAllFieldsDto> addMessageToTopic(@PathVariable String topicId,
                                                        @RequestBody MessageWithoutTopicDto message) {
        return ResponseEntity.ok(topicService.addMessage(topicId, message));
    }

    @PutMapping ("/{topicId}/message")
    ResponseEntity<TopicAllFieldsDto> updateMessage(@PathVariable String topicId,
                                                        @RequestBody MessageWithoutTopicDto message) {
        return ResponseEntity.ok(topicService.updateMessage(topicId, message));
    }
}
