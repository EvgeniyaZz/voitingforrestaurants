package project.voting.model;

import project.voting.HasId;

import javax.persistence.*;

@Entity
@Table(name = "meal")
public class Meal extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Meal() {
    }

    public Meal(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}