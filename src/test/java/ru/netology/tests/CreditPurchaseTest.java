package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Card;
import ru.netology.data.DbHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;

public class CreditPurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open(System.getProperty("sut.url"));
        DbHelper.clearDB();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @SneakyThrows
    @Test
    void shouldBuyByValidCard() {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkSuccessNotification();
        assertEquals("APPROVED", DbHelper.getCreditStatus());
    }

    @SneakyThrows
    @Test
    void shouldNotBuyByValidCard()  {
        Card card = new Card(getDeclinedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkDeclineNotification();
        assertEquals("DECLINED", DbHelper.getPaymentStatus());
    }

    @Test
    void shouldNotBuyWithoutCardNumber() {
        Card card = new Card("", getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyByShortCardNumber() {
        Card card = new Card(getShortCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyByNotValidCardNumber() {
        Card card = new Card(getInvalidCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkDeclineNotification();
    }
    @Test
    void shouldNotBuyWithoutMonth()  {
        Card card = new Card(getApprovedNumber(),"", getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }
    @Test
    void shouldNotBuyWithTheMonthWithOneFigure()  {
        Card card = new Card(getApprovedNumber(),"1", getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithTooBigMonthsNumber()  {
        Card card = new Card(getApprovedNumber(),"13", getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidDate();
    }

    @Test
    void shouldNotBuyWithMonthWithNulls()  {
        Card card = new Card(getApprovedNumber(),"00", getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidDate();
    }

    @Test
    void shouldNotBuyWithoutYear() {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), "", getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithYearWithOneFigure()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), "1", getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithLastYear()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getLastYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkExpiredDate();
    }

    @Test
    void shouldNotBuyWithoutName()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "", getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkRequiredField();
    }

    @Test
    void shouldNotBuyWithCyrillicName()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Иван Помидоров", getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithJustName()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Bono", getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithNameWithJustFigures()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "21285 06", getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithNameWithLettersAndFigures()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Richard2 Bordeaux", getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithNameWithSymbols()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Chip& Dale", getValidCvc());
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithoutCVV()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), getValidName(), "");
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkRequiredField();
    }

    @Test
    void shouldNotBuyWithCVVWithTwoFigures()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), getValidName(), "12");
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }
    @Test
    void shouldNotBuyWithCVVWithSymbol()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), getValidName(), "12!");
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithCVVWithNulls()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), getValidName(), "000");
        val mainPage = new MainPage();
        val creditPurchasePage = mainPage.buyInCredit();
        creditPurchasePage.fulfillData(card);
        creditPurchasePage.checkInvalidFormat();
    }


}
