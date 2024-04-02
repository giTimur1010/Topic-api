package ru.imanov.topictask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.imanov.topictask.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    @Query(nativeQuery = true, value = "DELETE FROM message WHERE id = :id")
    @Modifying
    void deleteMessageById(String id);
}
