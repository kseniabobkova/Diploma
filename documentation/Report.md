# Отчёт о проведённом тестировании

Согласно утверждённому  проведено тестирование веб-сервиса — автоматизированы сценарии (как позитивные, так и негативные) покупки тура, в том числе в кредит, а также протестирована заявленная поддержка СУБД MySQL и PostgreSQL.

На первом этапе было проведено мануальное тестирование. 

На втором этапе были написаны автотесты, и было проведено автоматизированное тестирование сервиса согласно утвержденному [Плану автоматизации тестирования](https://github.com/kseniabobkova/Diploma/blob/main/documentation/Test-plan.md).

Тестирование было проведено для двух БД - MySQL и PostgreSQL.

## Общее количество тест-кейсов: 44 

   * Успешных тест-кейсов 63,63% (28 авто-тестов)
   * Неуспешных тест-кейсов 36,37% (16 авто-тестов)

### Отчет Allure:

 ![](https://github.com/kseniabobkova/Diploma/blob/main/documentation/AllureReports/Allure_Report.jpg)
 
 ![](https://github.com/kseniabobkova/Diploma/blob/main/documentation/AllureReports/Allure_Report_CardPurchaseTest.jpg)
 
 ![](https://github.com/kseniabobkova/Diploma/blob/main/documentation/AllureReports/Allure_Report_CreditPurchaseTest.jpg)
  
По итогам тестирования заведены [Issues #1 - #11](https://github.com/kseniabobkova/Diploma/issues), из них #1 и #10 по итогам мануального тестирования. 

## Общие рекомендации

   * Разработка документации к приложению
   * Пересмотр логики вывода предупреждающих сообщений при некорректном заполнении поля
   * Исправление выявленных багов
