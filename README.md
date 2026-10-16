# MovieBox 🎬

MovieBox — Android-приложение для просмотра информации о фильмах и сериалах. Данные загружаются из **The Movie Database (TMDB) API**.

Проект создан для практического изучения современной Android-разработки на Kotlin и Jetpack Compose.

## ✨ Возможности

* **Главная** — популярные фильмы с трейлером главного фильма и трендовые ТВ-шоу
* **Поиск** фильмов по названию
* **Детали фильма** — описание, рейтинг, длительность, галерея кадров, трейлер
* **Избранное** — фильмы сохраняются локально в базе Room
* **Профиль** — выбор языка приложения (русский / английский); язык применяется и к интерфейсу, и к данным TMDB

## 🛠 Стек

* Kotlin, Coroutines, StateFlow
* Jetpack Compose + Material 3, Navigation Compose
* MVVM
* Retrofit, OkHttp, Moshi
* Room (+ Gson для конвертеров)
* Coil — загрузка изображений
* Koin — Dependency Injection

## 🏗 Архитектура

```text
Compose UI → ViewModel → Repository → Retrofit (TMDB) / Room (избранное)
```

* `Presentation` — экраны на Compose, отображают `UiState` из ViewModel.
* `ViewModel` — хранит состояние экрана в `StateFlow` и запускает загрузку данных.
* `Repository` — получает данные из сети или базы и через Mapper превращает DTO в доменные модели (`Movie`, `MovieDetails`, `TVShow`), чтобы UI не зависел от структуры API.
* `Network` — запросы к TMDB; интерсепторы добавляют токен авторизации и язык.

## 📂 Структура

```text
data/         network (API, DTO, интерсепторы), room, mapper, repository
domain/       модели приложения
presentation/ экраны, ViewModel, тема, компоненты
navigation/   навигация и нижнее меню
di/           модули Koin
```

## 🚀 Запуск

1. Клонируйте репозиторий и откройте его в Android Studio.
2. Получите токен TMDB: зарегистрируйтесь на [themoviedb.org](https://www.themoviedb.org/), откройте **Settings → API** и скопируйте **API Read Access Token** (длинная строка, начинается с `eyJ...`).
   > Нужен именно Read Access Token, а не короткий *API Key* — приложение передаёт его в заголовке `Authorization: Bearer`.
3. Скопируйте файл-шаблон в корне проекта:

   ```bash
   cp scratch.properties.example scratch.properties
   ```

4. Вставьте токен в `scratch.properties` **в двойных кавычках**:

   ```properties
   TMDB_API_KEY="eyJhbGciOiJIUzI1NiJ9..."
   ```

   Кавычки обязательны: значение подставляется в `BuildConfig` как строка, без них проект не соберётся.
5. Выполните **Sync Project with Gradle Files** и запустите приложение.

`scratch.properties` указан в `.gitignore` — не коммитьте свой токен. Если файла нет, проект соберётся, но запросы к TMDB будут возвращать ошибку 401.

## 📚 TMDB

https://developer.themoviedb.org/

MovieBox не является официальным приложением TMDB.

## 👨‍💻 Автор

**i-am-a-saw**

https://github.com/i-am-a-saw
