package project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.voting.HasId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@ToString(callSuper = true, exclude = {"user", "restaurant"})
@NoArgsConstructor
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"registered", "user_id"}, name = "user_unique_voice_date_idx"))
public class Vote extends AbstractBaseEntity implements HasId {

    @Column(name = "registered", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate registered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference(value = "user-vote")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference(value = "restaurant-vote")
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate registered) {
        super(id);
        this.registered = registered;
    }

    public Vote(Integer id, LocalDate registered, User user, Restaurant restaurant) {
        this.id = id;
        this.registered = registered;
        this.user = user;
        this.restaurant = restaurant;
    }
}