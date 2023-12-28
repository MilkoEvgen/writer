### Задание:
Реализовать консольное CRUD приложение, которое 
взаимодействует с БД и позволяет выполнять все CRUD операции 
над сущностями:<br/>
- **Writer** (id, firstName, lastName, List<Post> posts)
- **Post** (id, content, created, updated, List<Label> labels)
- **Label** (id, name)
- **PostStatus** (enum ACTIVE, UNDER_REVIEW, DELETED)
### Требования:
1. Все CRUD операции для каждой из сущностей
2. Придерживаться подхода MVC
3. Для сборки проекта использовать Maven
4. Для взаимодействия с БД - Hibernate
5. Для конфигурирования Hibernate - аннотации
6. Инициализация БД должна быт реализована с помощью flyway
7. Сервисный слой приложения должен быть покрыт юнит тестами (junit + mockito).


**Технологии**: Java, PostgreSQL, Hibernate, Flyway, Maven
___
### Инструкция по запуску проекта:
1. Установить Java и Maven
2. Склонировать проект себе на компьютер `git clone https://github.com/MilkoEvgen/writer.git`
3. Перейти в папку проекта и выполнить команду `mvn clean install`
4. Перейти в папку target и выполнить команду `java -jar Writer-1.0-SNAPSHOT.jar`
