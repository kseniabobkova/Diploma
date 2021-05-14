package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

        private final SelenideElement HEADING = $$("h2").find(exactText("Путешествие дня"));
        private final SelenideElement BUTTONBUY = $$("button").find(exactText("Купить"));
        private final SelenideElement BUTTONCREDIT = $$("button").find(exactText("Купить в кредит"));

        public MainPage() {
            HEADING.shouldBe(Condition.visible);
        }

        public CardPurchasePage buy() {
            BUTTONBUY.click();
            return new CardPurchasePage();

        }

        public CreditPurchasePage buyInCredit() {
            BUTTONCREDIT.click();
            return new CreditPurchasePage();
        }
    }
