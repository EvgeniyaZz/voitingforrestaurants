package project.voting.model;

import project.voting.HasId;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity implements HasId {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Meal> menu;

    @Column(name = "added")
    private Date added;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Date added) {
        super(id, name);
        this.added = added;
    }

    public void setMenu(List<Meal> menu) {
        this.menu = menu;
    }
}