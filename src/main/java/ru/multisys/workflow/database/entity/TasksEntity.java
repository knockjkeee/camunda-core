package ru.multisys.workflow.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.multisys.workflow.domain.StateTicket;

import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 11:43
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "tasks")
public class TasksEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "processInstanceId")
    String processInstanceId;
    @Column(name = "ticketID")
    String ticketID;
    @Column(name = "ticketNumber")
    String ticketNumber;
    @Column(name = "customerUserID")
    String customerUserID;
    @Column(name = "userID")
    String userID;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    StateTicket state;

    @Column(name = "initStamp")
    Instant initStamp;

    @Column(name = "startStamp")
    Instant startStamp;

    @Column(name = "workStamp")
    Instant workStamp;

    @Column(name = "endStamp")
    Instant endStamp;

    @Column(name = "closeStamp")
    Instant closeStamp;

    @OneToMany(mappedBy = "tasksEntity")
//            @OneToMany
//            @JoinColumn(name = "configurationModule_id")
    List<ExternalHistoryEntity> externalHistory;
}
