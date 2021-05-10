package ru.netology.tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.Card;
import ru.netology.data.DbHelper;
import ru.netology.page.MainPage;
import ru.netology.page.CardPurchasePage;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;


public class CardPurchaseTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("http://localhost:8080");
        DbHelper.clearDB();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @Test
    void shouldBuyByValidApprovedCard() throws SQLException {
        Card card = new Card(getApprovedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkSuccessNotification();
        assertEquals("APPROVED", DbHelper.getPaymentStatus());
    }

    @Test
    void shouldNotBuyByValidDeclinedCard() throws SQLException {
        Card card = new Card(getDeclinedNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkDeclineNotification();
        assertEquals("DECLINED", DbHelper.getPaymentStatus());
    }

    @Test
    void shouldNotBuyWithoutCardNumber()  {
        Card card = new Card("", getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyByShortCardNumber()  {
        Card card = new Card(getShortCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyByNotValidCardNumber()  {
        Card card = new Card(getInvalidCardNumber(), getCurrentMonth(), getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkDeclineNotification();
    }

    @Test
    void shouldNotBuyWithoutMonth()  {
        Card card = new Card(getApprovedNumber(),"", getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }
    @Test
    void shouldNotBuyWithTheMonthWithOneFigure()  {
        Card card = new Card(getApprovedNumber(),"1", getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithTooBigMonthsNumber()  {
        Card card = new Card(getApprovedNumber(),"13", getNextYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidDate();
    }

    @Test
    void shouldNotBuyWithoutYear() {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), "", getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithYearWithOneFigure()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), "1", getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithLastYear()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getLastYear(), getValidName(), getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkExpiredDate();
    }

    @Test
    void shouldNotBuyWithoutName()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "", getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkRequiredField();
    }

    @Test
    void shouldNotBuyWithCyrillicName()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Иван Помидоров", getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithJustName()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Bono", getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithNameWithJustFigures()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "21285 06", getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithNameWithLettersAndFigures()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Richard2 Bordeaux", getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithNameWithSymbols()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), "Chip& Dale", getValidCvc());
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    @Test
    void shouldNotBuyWithoutCVV()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), getValidName(), "");
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkRequiredField();
    }

    @Test
    void shouldNotBuyWithCVVWithTwoFigures()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), getValidName(), "12");
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }
    @Test
    void shouldNotBuyWithCVVWithSymbol()  {
        Card card = new Card(getApprovedNumber(),getCurrentMonth(), getNextYear(), getValidName(), "12!");
        val mainPage = new MainPage();
        mainPage.buy();
        val cardPurchasePage = new CardPurchasePage();
        cardPurchasePage.fulfillData(card);
        cardPurchasePage.checkInvalidFormat();
    }

    }


