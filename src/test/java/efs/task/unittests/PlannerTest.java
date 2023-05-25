package efs.task.unittests;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlannerTest {
    private Planner planner = new Planner();

    @BeforeEach
    void setUp() {
        planner = new Planner();
    }

    @ParameterizedTest
    @EnumSource(ActivityLevel.class)
    void checkDailyCalorieRequirement(ActivityLevel activityLevel) {
        User user = TestConstants.TEST_USER;
        Map<ActivityLevel, Integer> caloriesOnActivityLevel = TestConstants.CALORIES_ON_ACTIVITY_LEVEL;

        int calories = planner.calculateDailyCaloriesDemand(user, activityLevel);

        assertEquals(caloriesOnActivityLevel.get(activityLevel), calories);
    }

    @Test
    void checkRequiredDailyIntake(){
        User user = TestConstants.TEST_USER;
        DailyIntake dailyIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        DailyIntake calculatedDailyIntake = planner.calculateDailyIntake(user);

        assertEquals(dailyIntake.getCalories(), calculatedDailyIntake.getCalories());
        assertEquals(dailyIntake.getProtein(), calculatedDailyIntake.getProtein());
        assertEquals(dailyIntake.getFat(), calculatedDailyIntake.getFat());
        assertEquals(dailyIntake.getCarbohydrate(), calculatedDailyIntake.getCarbohydrate());
    }
}
