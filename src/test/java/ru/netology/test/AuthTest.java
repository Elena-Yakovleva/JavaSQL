package ru.netology.test;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.*;

import ru.netology.data.DataHelper;
import ru.netology.page.DashBoardPage;
import ru.netology.page.LoginPage;

import static java.nio.channels.Selector.open;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.netology.data.DataHelper.getRandomUser;
import static ru.netology.data.DataHelper.getValidAuthInfo;
import static ru.netology.data.SQLHelper.*;


public class AuthTest {

    LoginPage loginPage;


    @BeforeEach
    public void setup() {
        loginPage = Selenide.open("http://localhost:9999/", LoginPage.class);
    }

//@AfterAll
//    static void cleanAll() {
//    cleanDataBase();
//}

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
        assertTrue(dashBoardPage.getDashBoardPage());
    }

    @Test
    @DisplayName("Отказ в верификации при неверном коде")
    public void shouldInvalidVerificationCode() {
        var verificationPage = loginPage.getValidLogin();
        verificationPage.randomVerify();
        assertTrue(verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз."));
    }

    @Test
    @DisplayName("Отказ в авторизации для отсутствующего пользователя")

    public void shouldInvalidUser() {
        loginPage.login(getRandomUser());
        assertTrue(loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль"));

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

        loginPage.getOpenPage();
        verificationPage = loginPage.getValidLogin();
        verificationPage.randomVerify();

        assertTrue(verificationPage.verifyErrorNotification("Ошибка! \nПревышено количество попыток ввода кода!"));
    }


}
