package project.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.voting.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "added"}, name = "restaurant_date_idx"))
public class Restaurant extends AbstractNamedEntity implements HasId {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference(value = "restaurant-meal")
    private List<Meal> menu;

    @Column(name = "added", nullable = false)
    @NotNull
    private LocalDate added;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference(value = "restaurant-vote")
    private List<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.id, restaurant.name, restaurant.added);
    }

    public Restaurant(Integer id, String name) {
        this(id, name, LocalDate.now());
    }

    public Restaurant(Integer id, String name, LocalDate added) {
        super(id, name);
        this.added = added;
    }

    public List<Meal> getMenu() {
        return menu;
    }

    public LocalDate getAdded() {
        return added;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setMenu(List<Meal> menu) {
        this.menu = menu;
    }

    public void setAdded(LocalDate added) {
        this.added = added;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", added=" + added +
                '}';
    }
}