package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class FormTest {
    public String newDatePlusFormat(int plusDay, String dateFormat) {
        return LocalDate.now().plusDays(plusDay).format(DateTimeFormatter.ofPattern(dateFormat));
    }

    @Test
    void shouldTestV1() {
        Configuration.holdBrowserOpen = false;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Воронеж");
        $("[data-test-id=date] input").doubleClick().sendKeys(newDatePlusFormat(4, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Васильев-Иванов Василий");
        $("[data-test-id=phone] input").setValue("+78002225577");
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=notification]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestV2() {
        Configuration.holdBrowserOpen = false;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Во");
        $x("//*[contains(text(),'Воронеж')]").click();
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.ARROW_DOWN, Keys.ARROW_RIGHT, Keys.ENTER);
        $("[data-test-id=name] input").setValue("Васильев-Иванов Василий");
        $("[data-test-id=phone] input").setValue("+78002225577");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification]").shouldHave(Condition.text("Встреча успешно забронирована на " + newDatePlusFormat(4, "dd.MM.yyyy")), Duration.ofSeconds(15)).should(visible);
    }
}
