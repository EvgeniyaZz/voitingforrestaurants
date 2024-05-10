package project.voting.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import project.voting.model.Meal;
import project.voting.model.Restaurant;
import project.voting.service.MealService;
import project.voting.to.MealTo;
import project.voting.util.MealUtil;
import project.voting.util.exception.NotFoundException;
import project.voting.web.AbstractControllerTest;
import project.voting.web.restaurant.AdminRestaurantRestController;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.voting.MealTestData.*;
import static project.voting.RestaurantTestData.RESTAURANT1_ID;
import static project.voting.TestUtil.userHttpBasic;
import static project.voting.UserTestData.admin;
import static project.voting.UserTestData.user1;
import static project.voting.web.json.JsonUtil.writeValue;

class AdminMealRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = AdminRestaurantRestController.REST_URL + "/" + RESTAURANT1_ID + "/meals/";

    @Autowired
    private MealService mealService;

    @Test
    void create() throws Exception {
        MealTo mealTo = new MealTo(null, "newMeal", 500);
        Meal newMeal = MealUtil.createNewFromTo(mealTo);
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMeal)))
                .andDo(print())
                .andExpect(status().isCreated());

        Meal created = MEAL_MATCHER.readFromJson(action);

        int newId = created.id();
        newMeal.setId(newId);

        Restaurant restaurant = created.getRestaurant();
        newMeal.setRestaurant(restaurant);

        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(newId, RESTAURANT1_ID), newMeal);
    }

    @Test
    void update() throws Exception {
        MealTo updatedTo = new MealTo(null, "newName", 333);
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealService.get(MEAL1_ID, RESTAURANT1_ID), MealUtil.updateFromTo(new Meal(meal1), updatedTo));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL1_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, RESTAURANT1_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + MEAL4_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal1));
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL4_ID)
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(admin)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meals1));
    }

    @Test
    void createInvalid() throws Exception {
        MealTo mealTo = new MealTo(null, "", 0);
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(mealTo)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateInvalid() throws Exception {
        MealTo updatedTo = new MealTo(MEAL1_ID, " ", 50);
        perform(MockMvcRequestBuilders.put(REST_URL + MEAL1_ID)
                .with(userHttpBasic(admin))
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}