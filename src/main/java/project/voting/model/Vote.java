package project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.voting.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote", uniqueConstraints = @UniqueConstraint(columnNames = {"registered", "user_id"}, name = "user_unique_voice_date_idx"))
public class Vote extends AbstractBaseEntity implements HasId {

    @Column(name = "registered", nullable = false)
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

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.id, vote.getRegistered(), vote.getUser(), vote.getRestaurant());
    }

    public Vote(Integer id, LocalDate registered) {
        super(id);
        this.registered = registered;
    }

    public Vote(Integer id, LocalDate registered, User user, Restaurant restaurant) {
        this.id=id;
        this.registered=registered;
        this.user=user;
        this.restaurant=restaurant;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public User getUser() {
        return user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "id=" + id +
                ", registered=" + registered +
                '}';
    }
}