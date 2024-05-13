package project.voting.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import project.voting.HasId;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId {

    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name +
                '}';
    }
}