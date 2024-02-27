package ru.multisys.workflow.database.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @author knockjkeee
 * @created 20/02/2024 - 11:47
 */

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@Entity
@Table(name = "externalHistory")
public class ExternalHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "target_state")
    String targetState;

    @Column(name = "external_state")
    String externalState;

//    Europe/Moscow
    @Column(name = "state_transer")
    Boolean stateTransfer;

    @Column(name = "time_stamp")
    String timeStamp;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "tasksEntity_id")
    private TasksEntity tasksEntity;
}
