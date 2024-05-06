package project.voting.to;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

public class VoteTo extends BaseTo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    private LocalDate registered;

    public VoteTo() {
    }

    public VoteTo(Integer id, LocalDate registered) {
        super(id);
        this.registered = registered;
    }

    public LocalDate getRegistered() {
        return registered;
    }

    public void setRegistered(LocalDate registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "VoteTo{" +
                "id=" + id +
                ", registered=" + registered +
                '}';
    }
}