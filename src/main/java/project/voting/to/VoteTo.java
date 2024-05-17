package project.voting.to;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    @NotNull
    int restaurantId;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    LocalDate registered;

    public VoteTo(Integer id, int restaurantId, LocalDate registered) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.registered = registered;
    }
}