package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

        private final SelenideElement HEADING = $$("h2").find(exactText("Путешествие дня"));
        private final SelenideElement BUTTON_BUY = $$("button").find(exactText("Купить"));
        private final SelenideElement BUTTON_CREDIT = $$("button").find(exactText("Купить в кредит"));

        public MainPage() {
            HEADING.shouldBe(Condition.visible);
        }

        public CardPurchasePage buy() {
            BUTTON_BUY.click();
            return new CardPurchasePage();

        }

        public CreditPurchasePage buyInCredit() {
            BUTTON_CREDIT.click();
            return new CreditPurchasePage();
        }
    }
