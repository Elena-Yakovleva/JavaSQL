package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static java.awt.SystemColor.info;
import static ru.netology.data.DataHelper.getRandomUser;
import static ru.netology.data.DataHelper.getValidAuthInfo;

public class LoginPage {

    private SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");
    private SelenideElement fieldLogin = $("[data-test-id='login'] input");
    private SelenideElement fieldPassword = $("[data-test-id='password'] input");
    private SelenideElement loginButton = $("[data-test-id='action-login']");


    public boolean verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(Condition.visible);
        return true;
    }

    public void login (DataHelper.AuthInfo info) {
        fieldLogin.setValue(info.getLogin());
        fieldPassword.setValue(info.getPassword());
        loginButton.click();
    }

    public VerificationPage getValidLogin() {
        login(getValidAuthInfo());
        return new VerificationPage();
    }

    public void getOpenPage(){
        Selenide.open("http://localhost:9999/", LoginPage.class);
    }



}
