package ru.netology.selenide;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class FormTest {
    @Test
    void shouldTestV1() {
        Configuration.holdBrowserOpen = false;
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Воронеж");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String date = dateFormat.format(Date.valueOf(LocalDate.now().plusDays(4)));
        $("[data-test-id=date] input").doubleClick().sendKeys(date);
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
        LocalDate date = LocalDate.now().plusDays(4);
        $("[data-test-id=date] input").click();
        $x("//table //*[contains(text(), '" + date.getDayOfMonth() + "')]").click();
        $("[data-test-id=name] input").setValue("Васильев-Иванов Василий");
        $("[data-test-id=phone] input").setValue("+78002225577");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=notification]").should(visible, Duration.ofSeconds(15));
    }
}
