package project.voting.to;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
public class MealTo extends BaseTo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @NotNull
    private int price;

    public MealTo() {
    }

    public MealTo(Integer id, String name, int price) {
        super(id);
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}