package project.voting;

import project.voting.model.Dish;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = 1;
    public static final int DISH4_ID = 4;

    public static final Dish dish1 = new Dish(DISH1_ID, "Средиземноморский суп с морепродуктами", 670, LocalDate.now());
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "Палтус с цветной капустой и миндальным соусом", 780, LocalDate.now());
    public static final Dish dish3 = new Dish(DISH1_ID + 2, "Бриошь с камамбером", 350, LocalDate.now());

    public static final Dish dish4 = new Dish(DISH4_ID, "Томатный суп с беконом", 490, LocalDate.now());
    public static final Dish dish5 = new Dish(DISH1_ID + 4, "Копченый колбаски", 750, LocalDate.now());
    public static final Dish dish6 = new Dish(DISH1_ID + 5, "Овощи гриль", 340, LocalDate.now());
    public static final Dish dish7 = new Dish(DISH1_ID + 6, "Пирог Флорида", 440, LocalDate.now());

    public static final Dish dish8 = new Dish(DISH1_ID + 7, "Традиционный борщ с копченым ребром поросенка", 590, LocalDate.now());
    public static final Dish dish9 = new Dish(DISH1_ID + 8, "Ребро поросенка BBQ", 1300, LocalDate.now());
    public static final Dish dish10 = new Dish(DISH1_ID + 9, "Большой зеленый салат", 790, LocalDate.now());
    public static final Dish dish11 = new Dish(DISH1_ID + 10, "Брауни с карамелью и арахисом", 500, LocalDate.now());

    public static final List<Dish> dishes1 = List.of(dish1, dish2, dish3);
    public static final List<Dish> dishes2 = List.of(dish4, dish5, dish6, dish7);
    public static final List<Dish> dishes3 = List.of(dish8, dish9, dish10, dish11);

    public static Dish getNew() {
        return new Dish(null, "newDish", 500, LocalDate.now());
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "Тыквенный пирог", 390, LocalDate.now());
    }
}
