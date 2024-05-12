package project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.voting.HasId;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Setter
@Getter
@Entity
@Table(name = "meal", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "restaurant_id"}, name = "meal_restaurant_unique_date_idx"))
public class Meal extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference(value = "restaurant-meal")
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(Meal meal) {
        this(meal.id, meal.name, meal.price);
    }

    public Meal(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}