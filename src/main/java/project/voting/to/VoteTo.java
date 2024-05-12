package project.voting.to;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class VoteTo extends BaseTo {

    @NotNull
    int restaurantId;

    @NotNull
    private LocalDate registered;

    public VoteTo() {
    }

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