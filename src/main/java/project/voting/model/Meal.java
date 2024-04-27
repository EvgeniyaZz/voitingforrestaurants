package project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import project.voting.HasId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "meal", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "restaurant_id"}, name = "meal_restaurant_unique_date_idx"))
public class Meal extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    @NotNull
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Restaurant getRestaurant() {
        return restaurant;
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