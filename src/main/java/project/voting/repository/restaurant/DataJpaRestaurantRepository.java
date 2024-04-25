package project.voting.repository.restaurant;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import project.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final CrudRestaurantRepository crudRestaurantRepository;

    public DataJpaRestaurantRepository(CrudRestaurantRepository crudRestaurantRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return crudRestaurantRepository.save(restaurant);
    }

    @Override
    public boolean delete(int id) {
        return crudRestaurantRepository.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crudRestaurantRepository.findById(id).orElse(null);
    }

    @Override
    public List<Restaurant> getAll() {
        return crudRestaurantRepository.findAll(SORT_NAME);
    }

    @Override
    public Restaurant getWithMeals(int id) {
        return crudRestaurantRepository.getWithMeals(id);
    }

    @Override
    public List<Restaurant> getByDate(LocalDate added) {
        return crudRestaurantRepository.getByDate(added);
    }
}
