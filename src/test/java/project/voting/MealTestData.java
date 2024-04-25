package project.voting;

import project.voting.model.Meal;

import java.util.List;

import static project.voting.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Meal.class, "restaurant");

    public static final int MEAL1_ID = START_SEQ + 6;

    public static final Meal meal1 = new Meal(MEAL1_ID, "Средиземноморский суп с морепродуктами", 670);
    public static final Meal meal2 = new Meal(MEAL1_ID + 1, "Палтус с цветной капустой и миндальным соусом", 780);
    public static final Meal meal3 = new Meal(MEAL1_ID + 2, "Бриошь с камамбером", 350);

    public static final Meal meal4 = new Meal(MEAL1_ID + 3, "Томатный суп с беконом", 490);
    public static final Meal meal5 = new Meal(MEAL1_ID + 4, "Копченый колбаски", 750);
    public static final Meal meal6 = new Meal(MEAL1_ID + 5, "Овощи гриль", 340);
    public static final Meal meal7 = new Meal(MEAL1_ID + 6, "Пирог Флорида", 440);

    public static final Meal meal8 = new Meal(MEAL1_ID + 7, "Традиционный борщ с копченым ребром поросенка", 590);
    public static final Meal meal9 = new Meal(MEAL1_ID + 8, "Ребро поросенка BBQ", 1300);
    public static final Meal meal10 = new Meal(MEAL1_ID + 9, "Большой зеленый салат", 790);
    public static final Meal meal11 = new Meal(MEAL1_ID + 10, "Брауни с карамелью и арахисом", 500);

    public static final List<Meal> meals = List.of(meal1, meal2, meal3);
    public static final List<Meal> allMeals = List.of(meal1, meal2, meal3, meal4, meal5, meal6, meal7, meal8, meal9, meal10, meal11);

    public static Meal getNew() {
        return new Meal(null, "newMeal", 500);
    }

    public static Meal getUpdated() {
        return new Meal(MEAL1_ID, "Тыквенный пирог", 390);
    }
}
