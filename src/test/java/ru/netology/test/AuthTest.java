package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import ru.netology.page.LoginPage;

import static ru.netology.data.DataHelper.getRandomUser;
import static ru.netology.data.SQLHelper.*;


public class AuthTest {

    LoginPage loginPage;


    @BeforeEach
    public void setup() {
        loginPage = Selenide.open("http://localhost:9999/", LoginPage.class);
    }

    @AfterAll
    static void cleanAll() {
        cleanDataBase();
    }

    @AfterEach
    void cleanCode() {
        cleanAuthCode();
    }

    @Test
    @DisplayName("Валидная авторизация и верификация входа в ЛК")
    public void shouldGetValidVerification() {
        var verificationPage = loginPage.getValidLogin();
        var verificationCode = getCode();
        var dashBoardPage = verificationPage.validVerify(verificationCode);
    }

    @Test
    @DisplayName("Отказ в верификации при неверном коде")
    public void shouldInvalidVerificationCode() {
        var verificationPage = loginPage.getValidLogin();
        verificationPage.randomVerify();
        verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }

    @Test
    @DisplayName("Отказ в авторизации для отсутствующего пользователя")

    public void shouldInvalidUser() {
        loginPage.login(getRandomUser());
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");

    }

    @Test
    @DisplayName("Превышение количества попыток ввода кода")
    public void shouldExceededNumberCodeAttempts() {
        var verificationPage = loginPage.getValidLogin();
        verificationPage.randomVerify();
        verificationPage.getClosePage();

        loginPage.getOpenPage();
        verificationPage = loginPage.getValidLogin();
        verificationPage.randomVerify();
        verificationPage.getClosePage();

        loginPage.getOpenPage();
        verificationPage = loginPage.getValidLogin();
        verificationPage.randomVerify();
        verificationPage.getClosePage();

        loginPage.getOpenPage();
        verificationPage = loginPage.getValidLogin();
        verificationPage.randomVerify();

        verificationPage.verifyErrorNotification("Ошибка! \nПревышено количество попыток ввода кода!");
    }


}
