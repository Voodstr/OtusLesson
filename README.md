# Otus - проектная работа
Обучение происходило путем наращивания фич для приложения по поиску фильмов в базе.


## Основной функционал приложения

1. Перемещени по списку с возможностью добавления и удаления в избранное. Cписок фильмов запрашивается посредством API (Retrofit), а потом сохраняются в базу данных (Room). Также поддерживается пагинация.

  <img src="/addFavTest.gif" width="300">
  
2.  Подробная информация о фильме. Возможность добавить в избранное, поделиться информацией о фильме, а так же поставить напоминание о том чтоб посмотреть фильм.

<img src="/aboutTest2.gif" width="300">

3.  При помощи сервиса Firebase можно отправлять пользователям уведомления с фильмом, который открывается сразу в приложении.

<img src="/Notification_test.gif" width="1080">

4. В Firebase настроено CrashLytics - сервис по сбору ошибок работы приложений.


<img src="/crashlytics.jpg" width="1080">

5. В приложении реализован ряд тестов. Espresso. Robolectric. Unit и Интеграционное тестирование. 

6. Приложение поддерживает несколько версий (Flavors).
