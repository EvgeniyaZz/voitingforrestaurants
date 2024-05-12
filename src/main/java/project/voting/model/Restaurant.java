package project.voting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.voting.HasId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "restaurant", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "added"}, name = "restaurant_date_idx"))
public class Restaurant extends AbstractNamedEntity implements HasId {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference(value = "restaurant-meal")
    private List<Meal> menu;

    @Column(name = "added", nullable = false, columnDefinition = "date default now()")
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

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", added=" + added +
                '}';
    }
}