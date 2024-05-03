package project.voting.repository.restaurant;

import org.springframework.stereotype.Repository;
import project.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DataJpaRestaurantRepository implements RestaurantRepository {

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
        return crudRestaurantRepository.getAll();
    }

    @Override
    public Restaurant getWithMeals(int id) {
        return crudRestaurantRepository.getWithMeals(id);
    }

    @Override
    public List<Restaurant> getByDate(LocalDate added) {
        return crudRestaurantRepository.getByDate(added);
    }

    @Override
    public List<Restaurant> getWithMealsByDate(LocalDate added) {
        return crudRestaurantRepository.getWithMealsByDate(added);
    }
}
