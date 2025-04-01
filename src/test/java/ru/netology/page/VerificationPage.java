package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.data.DataHelper.generateRandomVerificationCode;

public class VerificationPage {

    public SelenideElement verifyPageHeader = $("h2");
    public SelenideElement fieldCode = $("[data-test-id='code'] input");
    public SelenideElement verifyButton = $("[data-test-id='action-verify']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification'] .notification__content");

    public VerificationPage() {
        verifyPageHeader.shouldBe(Condition.visible, Condition.text("Интернет Банк"));
    }

    public boolean verifyErrorNotification(String expectedText) {
        errorNotification.shouldHave(exactText(expectedText)).shouldBe(Condition.visible);
        return true;
    }

    public void verify(String verificationCode) {
        fieldCode.setValue(verificationCode);
        verifyButton.click();
    }

    public DashBoardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashBoardPage();
    }

    public void randomVerify() {
        verify(generateRandomVerificationCode().getCode());

    }

    public void getClosePage() {
        Selenide.closeWindow();
    }

}
