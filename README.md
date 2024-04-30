## Задание: Реализация аутентификации и авторизации с использованием Spring Security и JWT

<details>
<summary>Полный текст задания</summary>
 Цель задания: Создать базовое веб-приложение с использованием Spring Security и JWT для аутентификации и авторизации пользователей.

Шаги задания:

Настройка проекта:
	Создайте новый проект Spring Boot.
Настройка конфигурации безопасности:
	Настройте базовую конфигурацию Spring Security для вашего приложения.
	Используйте JWT для аутентификации пользователей.
	Создайте класс для генерации и проверки JWT токенов.
Реализация контроллеров:
	Создайте контроллеры для аутентификации и регистрации пользователей.
	Реализуйте методы для создания нового пользователя и генерации JWT токена при успешной аутентификации.
	Реализуйте сохранение пользователей в базу данных PostgreSQL.
	Добавьте поддержку ролей пользователей и настройте авторизацию на основе ролей.
Тестирование:
	Напишите модульные тесты для контроллеров и сервисов.
	Убедитесь, что аутентификация и авторизация работают корректно.
	Проверьте, что только аутентифицированные пользователи имеют доступ к защищенным ресурсам.
Документация:
	Добавьте краткую документацию к вашему API с использованием Swagger или OpenAPI.
Результат задания: Рабочее веб-приложение с базовой аутентификацией и авторизацией на основе Spring Security и JWT, сопровождаемое модульными тестами и краткой документацией к API.
</details>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white "Java 11")
![Maven](https://img.shields.io/badge/Maven-green.svg?style=for-the-badge&logo=mockito&logoColor=white "Maven")
![Spring](https://img.shields.io/badge/Spring-blueviolet.svg?style=for-the-badge&logo=spring&logoColor=white "Spring")
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![GitHub](https://img.shields.io/badge/git-%23121011.svg?style=for-the-badge&logo=github&logoColor=white "Git")
+ ЯП: *Java 17*
+ Автоматизация сборки: *Maven*
+ Фреймворк: *Spring*
+ База данных: *PostgreSQL*
+ Контроль версий: *Git*

Для запуска приложения необходимо в aplication.properties указать настройки БД
