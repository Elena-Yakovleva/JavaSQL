package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class DashBoardPage {

    private SelenideElement dashBoardHeader = $ ("[data-test-id='dashboard']");

    public DashBoardPage() {
    }

    public boolean getDashBoardPage() {
        dashBoardHeader.shouldBe(Condition.visible, Condition.text("Личный кабинет"));
        return true;
    }
}
