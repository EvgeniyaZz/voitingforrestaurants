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
@ToString(callSuper = true, exclude = "restaurant")
@NoArgsConstructor
@Table(name = "dish", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "added"}, name = "dish_unique_date_idx"))
public class Dish extends AbstractNamedEntity implements HasId {

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "added", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate added;

    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    @JsonBackReference(value = "restaurant-dish")
    private Restaurant restaurant;

    public Dish(Dish dish) {
        this(dish.id, dish.name, dish.price, dish.added);
    }

    public Dish(Integer id, String name, int price, LocalDate added) {
        super(id, name);
        this.price = price;
        this.added = added;
    }
}