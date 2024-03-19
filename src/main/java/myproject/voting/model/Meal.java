package myproject.voting.model;

import myproject.voting.HasId;

import javax.persistence.*;

@Entity
@Table(name = "meal")
public class Meal extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;
}