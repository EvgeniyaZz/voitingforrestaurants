package myproject.voting.model;

import myproject.voting.HasId;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity implements HasId {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private List<Meal> menu;

    @Column(name = "added")
    private Date date;
}