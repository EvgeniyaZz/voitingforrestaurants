package project.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import project.voting.HasId;

@Value
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}