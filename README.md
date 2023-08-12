## Udemy Course 
### Telegram Taboo bot

Before launching the project, create the `application-local.yml` file and populate it with your bot's username and token using the following format:
```
env:
  variables:
    telegram:
      username: <YoutBotUsername>
      token: <YourBotToken>
```
Additionally, include the following VM options in the Edit Configuration Settings: `-Dspring.profiles.active=local`

