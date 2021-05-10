# Процедура запуска автотестов

## Предусловия:

На компьютере установлены: 

    • IntelliJ IDEA c JDK 11
    • Docker
    
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
       
