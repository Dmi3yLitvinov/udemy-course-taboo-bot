## Udemy Course 
### Telegram Taboo bot

Before launching the project, create the `application-local.yml` file and populate it with your property values using the following format:
```
env:
  variables:
    db:
      url: jdbc:postgresql://localhost:5432/<YourDatabase>
      username: <YourDatabaseUsername>
      password: <YourDatabaseUserPassword>
    telegram:
      username: <YourBotUsername>
      token: <YourBotToken>
```
Additionally, include the following VM options in the Edit Configuration Settings: `-Dspring.profiles.active=local`

