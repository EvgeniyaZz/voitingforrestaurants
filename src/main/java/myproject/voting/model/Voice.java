package myproject.voting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import myproject.voting.HasId;

import java.time.LocalDateTime;

@Entity
@Table(name = "voice")
public class Voice extends AbstractBaseEntity implements HasId {

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;
}