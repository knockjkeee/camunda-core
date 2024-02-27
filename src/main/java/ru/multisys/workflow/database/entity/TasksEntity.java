package ru.multisys.workflow.database.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.multisys.workflow.domain.StateTicket;

import java.util.List;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 11:43
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@Entity
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
    String initStamp;

    @Column(name = "startStamp")
    String startStamp;

    @Column(name = "workStamp")
    String workStamp;

    @Column(name = "endStamp")
    String endStamp;

    @Column(name = "closeStamp")
    String closeStamp;

    @OneToMany(mappedBy = "tasksEntity", fetch = FetchType.EAGER, cascade = {CascadeType.ALL, CascadeType.PERSIST})
//            @OneToMany
//            @JoinColumn(name = "configurationModule_id")
    List<ExternalHistoryEntity> externalHistory;
}
