# Дипломный проект профессии «Тестировщик»

  * [План автоматизации тестирования](https://github.com/kseniabobkova/Diploma/blob/main/documentation/Test-plan.md)
  * [Отчёт о проведённом тестировании](https://github.com/kseniabobkova/Diploma/blob/main/documentation/Report.md)
  * [Отчёт по итогам автоматизации](https://github.com/kseniabobkova/Diploma/blob/main/documentation/Summary.md)

# Процедура запуска автотестов

## Предусловия:

На компьютере установлены: 

    • Установить и запустить IntelliJ IDEA c JDK 11
    • Установить и запустить Docker Desktop
    • Установить и запустить Docker Compose
    
    
## Шаги:
    1. Ввести в терминале git clone https://github.com/kseniabobkova/Diploma.git
    2. Запустить контейнеры  с MySql, PostgreSQL и Node.js командой  
          docker-compose up
    3. Запустить SUT командой
        ◦ Для MySQL:
          java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar
          
        ◦ Для PostgreSQL:
          java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar
    4. Запустить  тесты командой
        ◦ Для MySQL 
          gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app allureReport
          
        ◦ Для PostgreSQ 
          gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app allureReport
      
    5. Для получения отчета Allure в браузере ввести команду 
           gradlew allureServe
       
