package ru.imanov.topictask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.imanov.topictask.entity.Topic;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM message WHERE id_topic = :id")
    int countMessagesOfTopic(String id);
}
