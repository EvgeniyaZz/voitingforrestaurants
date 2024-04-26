package project.voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import project.voting.HasId;

import javax.persistence.*;

@Entity
@Table(name = "meal")
public class Meal extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
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