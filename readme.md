# CFT_CRM

Тестовое задание: упрощенная CRM-система для управления продавцами и их транзакциями.

## Функциональность

### Продавцы (Seller)
- Получение списка всех продавцов
- Получение информации о конкретном продавце
- Создание нового продавца
- Обновление информации о продавце
- Удаление продавца (soft delete)

### Транзакции (Transaction)
- Получение списка всех транзакций
- Получение информации о конкретной транзакции
- Создание новой транзакции
- Получение всех транзакций конкретного продавца

### Аналитика
- Получение самого продуктивного продавца за:
  - день
  - месяц
  - квартал
  - год

  Самый продуктивный продавец — продавец с наибольшей суммой транзакций за выбранный период.

- Получение списка продавцов, у которых сумма транзакций за выбранный период меньше указанного значения.

## Технологии

- Java 21
- Spring Boot 3
- Spring Data JPA
- PostgreSQL
- H2 Database (для тестирования)
- Gradle
- JUnit 5
- Mockito

## Сборка проекта

```bash
./gradlew build
```

## Запуск проекта

### Через Docker

```bash
docker compose up --build
```

После запуска приложение будет доступно по адресу:

```text
http://localhost:8080
```

### Без Docker

Необходимо предварительно запустить PostgreSQL и создать БД.

Параметры подключения находятся в:

```text
src/main/resources/application.properties
```

Запуск:

```bash
./gradlew bootRun
```

## Тестирование

Запуск тестов:

```bash
./gradlew test
```

Генерация отчета покрытия:

```bash
./gradlew jacocoTestReport
```

Отчет будет доступен по пути:

```text
build/reports/jacoco/test/html/index.html
```

## API endpoints

### Sellers
- `GET /sellers`
- `GET /sellers/{id}`
- `POST /sellers`
- `PUT /sellers/{id}`
- `DELETE /sellers/{id}`
- `GET /sellers/{id}/transactions`

### Transactions
- `GET /transactions`
- `GET /transactions/{id}`
- `POST /transactions`

### Analytics
- `GET /analytics/topSeller`
- `GET /analytics/lessAmount`

## Примеры запросов

### Создание продавца

```http
POST /sellers
Content-Type: application/json
```

```json
{
  "name": "Ivan Ivanov",
  "contactInfo": "ivan@example.com"
}
```

### Создание транзакции

```http
POST /transactions
Content-Type: application/json
```

```json
{
  "sellerId": 1,
  "amount": 1500.50,
  "paymentType": "CARD"
}
```

### Получение топ-продавца за месяц

```http
GET /analytics/topSeller?period=month&date=2026-01
```

### Получение продавцов с суммой меньше указанной

```http
GET /analytics/lessAmount?amount=10000&startDate=2026-01-01&endDate=2026-01-31
```

## Дополнительно (не входит в обязательное задание)

Реализованы дополнительные улучшения:

- Docker и docker-compose для быстрого запуска приложения и PostgreSQL
- Swagger/OpenAPI документация

Swagger UI доступен по адресу:

```text
http://localhost:8080/swagger-ui.html
```

Также OpenAPI-файл находится в корне проекта.

## Структура проекта

```text
src/main/java
 ├── controller
 ├── service
 ├── repo
 ├── dto
 ├── models
 └── exception
```
