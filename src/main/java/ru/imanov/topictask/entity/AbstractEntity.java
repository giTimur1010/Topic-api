package ru.imanov.topictask.entity;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class AbstractEntity {
    @Column(name = "created")
    private String created;
}
