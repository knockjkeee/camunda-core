package ru.multisys.workflow.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 11:47
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "externalHistory")
public class ExternalHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "name")
    String name;
    @ManyToOne
    @JoinColumn(name="tasksEntity_id", nullable=false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private TasksEntity tasksEntity;
}
