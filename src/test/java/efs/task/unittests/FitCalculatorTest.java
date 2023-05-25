package efs.task.unittests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.management.ConstructorParameters;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        // given
        double weight = 89.2;
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietNotRecommended() {
        // given
        double weight = 69.5;
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
    }

    @Test
    void shouldThrowException_whenHeightIsZero() {

        double weight = 6.5;
        double height = 0;
        Class expectedException = IllegalArgumentException.class;

        assertThrows(expectedException, () -> FitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(name = "height = {0}")
    @ValueSource(doubles = { 1.72, 1.73, 1.74 })
    void shouldReturnTrue_whenDietRecommended(double height) {
        // given
        double weight = 89.2;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "height = {0}, weight = {1}")
    @CsvSource({
            "1.72, 89.2",
            "1.73, 90.2",
            "1.74, 91.2"
    })
    void shouldReturnTrue_whenDietRecommended(double height, double weight) {
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertTrue(recommended);
    }

    @ParameterizedTest(name = "height = {0}, weight = {1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_whenDietNotRecommended(double height, double weight) {
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        assertFalse(recommended);
    }

    @Test
    void userWithWorstBmiShouldBe() {
        double height = 1.79;
        double weight = 97.3;
        List<User> users = TestConstants.TEST_USERS_LIST;

        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(users);

        assertEquals(height, userWithWorstBMI.getHeight());
        assertEquals(weight, userWithWorstBMI.getWeight());
    }

    @Test
    void userWithWorstBmiNullList() {
        List<User> users = new ArrayList<>();

        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(users);

        assertNull(userWithWorstBMI);
    }

    @Test
    void listsShouldBeEqual() {
        List<User> users = TestConstants.TEST_USERS_LIST;
        double[] expected = TestConstants.TEST_USERS_BMI_SCORE;
        double[] bmiScores = FitCalculator.calculateBMIScore(users);

        assertArrayEquals(TestConstants.TEST_USERS_BMI_SCORE, bmiScores);
    }
}