package stringcalculator;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import stringcalculator.domain.Experssion;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class ExperssionTest {

    @DisplayName("NULL또는빈값")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @NullAndEmptySource
    void NULL또는빈값(String expression) {
        assertThatThrownBy(() -> {
            Experssion experssion = new Experssion(expression);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("수식이 입력되지 않았습니다.");
    }

    @DisplayName("수식개수검증")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ValueSource(strings = {"4 4", "+ 3", "3 +", "3 + 4 4"})
    void 수식개수검증(String expression) {
        assertThatThrownBy(() -> {
            Experssion experssion = new Experssion("3 + 4 +");
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("입력한 수식의 개수가 유효하지 않습니다.");
    }

    @DisplayName("연산자오류")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ValueSource(strings = {"3 + 4 & 5", "3 % 4 + 5", "3 + 4 4 5"})
    void 연산자오류(String expression) {
        assertThatThrownBy(() -> {
            Experssion experssion = new Experssion(expression);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("입력된 연산자가 사칙연산자가 아닙니다.");
    }

    @DisplayName("피연산자오류")
    @ParameterizedTest(name = "{displayName} [{index}] {arguments}")
    @ValueSource(strings = {"+ 4 - 5 +", "3 + 4 + *", "3 + 4 + A"})
    void 피연산자오류(String expression) {
        assertThatThrownBy(() -> {
            Experssion experssion = new Experssion(expression);
        }).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("입력된 피연산자가 숫자가 아닙니다");
    }

    @DisplayName("사칙연산")
    @ParameterizedTest(name = "{displayName} [{index}] {0} = {1}")
    @CsvSource(value = {"1 + 2,3", "2 - 1,1", "2 * 3,6", "6 / 2,3"})
    void 사칙연산(String expression, String expected) {
        Experssion experssion = new Experssion(expression);

        Assertions.assertThat(experssion.reduce()).isEqualTo(Integer.parseInt(expected));
    }

    @DisplayName("복합연산")
    @ParameterizedTest(name = "{displayName} [{index}] {0} = {1}")
    @CsvSource(value = {"3 + 4 - 2 * 6 / 5,6", "6 * 3 - 2 / 2,8", "2 + 18 - 5 * 2,30", "2 / 2 * 5 + 4,9"})
    void 복합연산(String expression, String expected) {
        Experssion experssion = new Experssion(expression);

        Assertions.assertThat(experssion.reduce()).isEqualTo(Integer.parseInt(expected));
    }
}
