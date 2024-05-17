package project.voting.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    int price;

    @NotNull
    LocalDate added;

    public DishTo(Integer id, String name, int price, LocalDate added) {
        super(id, name);
        this.price = price;
        this.added = added;
    }
}