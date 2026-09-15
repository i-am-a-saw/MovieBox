# MovieBox 🎬

MovieBox — Android-приложение для просмотра информации о фильмах. Данные загружаются из **The Movie Database (TMDB) API**.

Проект создан для практического изучения современной Android-разработки на Kotlin и Jetpack Compose.

## ✨ Возможности

* Популярные фильмы из TMDB
* Карточки фильмов с постерами и рейтингом
* Экран подробной информации
* Поиск фильмов
* Навигация между экранами
* Загрузка изображений через Coil

## 🛠 Стек

* Kotlin
* Jetpack Compose + Material 3
* MVVM
* StateFlow
* Retrofit
* Moshi
* OkHttp
* Coil 3
* Koin
* Navigation Compose
* Coroutines

## 🏗 Архитектура

Проект разделён на несколько слоёв, каждый из которых отвечает за свою задачу:

```text
Presentation
     ↓
 ViewModel
     ↓
Repository
     ↓
Network
     ↓
   TMDB
```

`Presentation` отвечает за UI и отображение состояния.

`ViewModel` управляет состоянием экрана и запускает операции загрузки данных.

`Repository` является промежуточным слоем между ViewModel и API. Он получает данные и преобразует их в модели приложения.

`Network` отвечает за HTTP-запросы к TMDB с помощью Retrofit, OkHttp и Moshi.

### Путь данных

```text
TMDB JSON
   ↓
MovieDto
   ↓
Mapper
   ↓
Movie
   ↓
ViewModel
   ↓
StateFlow
   ↓
Jetpack Compose
```

DTO описывает структуру ответа TMDB, а `Movie` содержит модель, используемую внутри приложения. Mapper преобразует данные между ними.

Такое разделение позволяет UI не зависеть напрямую от структуры API и упрощает дальнейшую разработку и отладку.

## 📂 Структура

```text
data/         API, DTO, Mapper, Repository
domain/       модели приложения
presentation/ экраны и ViewModel
navigation/   навигация
di/           Dependency Injection
```

## 🚀 Запуск

Для запуска проекта необходим TMDB API token.

Токен хранится локально и передаётся в приложение через `BuildConfig`.

После добавления токена откройте проект в Android Studio и запустите приложение.

## 📚 TMDB

https://developer.themoviedb.org/

MovieBox не является официальным приложением TMDB.

## 👨‍💻 Автор

**i-am-a-saw**

https://github.com/i-am-a-saw
