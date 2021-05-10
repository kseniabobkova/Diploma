package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$$;

public class MainPage {

        private final SelenideElement heading = $$("h2").find(exactText("Путешествие дня"));
        private final SelenideElement buttonBuy = $$("button").find(exactText("Купить"));
        private final SelenideElement buttonCredit = $$("button").find(exactText("Купить в кредит"));

        public MainPage() {
            heading.shouldBe(Condition.visible);
        }

        public CardPurchasePage buy() {
            buttonBuy.click();
            return new CardPurchasePage();

        }

        public CreditPurchasePage buyInCredit() {
            buttonCredit.click();
            return new CreditPurchasePage();
        }
    }
