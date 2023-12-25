### Задание:
Реализовать консольное CRUD приложение, которое 
взаимодействует с БД и позволяет выполнять все CRUD операции 
над сущностями:<br/>
- **Writer** (id, firstName, lastName, List<Post> posts)
- **Post** (id, content, created, updated, List<Label> labels)
- **Label** (id, name)
- **PostStatus** (enum ACTIVE, UNDER_REVIEW, DELETED)
### Требования:
1. Придерживаться шаблона MVC (пакеты model, repository, service, controller, view)
2. Для миграции БД использовать https://www.liquibase.org/
3. Сервисный слой приложения должен быть покрыт юнит тестами (junit + mockito).
4. Слои:
   - model - POJO клаcсы
   - repository - классы, реализующие доступ к БД
   - controller - обработка запросов от пользователя
   - view - все данные, необходимые для работы с консолью
5. Для импорта библиотек использовать Maven

**Технологии**: Java, MySQL, JDBC, Maven, Liquibase, JUnit, Mockito
___
### Инструкция по запуску проекта:
1. Установить Java и Maven
2. Склонировать проект себе на компьютер `git clone https://github.com/MilkoEvgen/writer.git`
3. Перейти в папку проекта и выполнить команду `mvn clean install`
4. Перейти в папку target и выполнить команду `java -jar Writer-1.0-SNAPSHOT.jar`
