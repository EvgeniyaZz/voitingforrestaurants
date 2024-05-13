package project.voting.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.time.LocalDate;

@Setter
@Getter
@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    @NotNull
    int restaurantId;

    @NotNull
    LocalDate registered;

    public VoteTo(Integer id, int restaurantId, LocalDate registered) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", registered=" + registered +
                '}';
    }
}