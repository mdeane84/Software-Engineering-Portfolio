# Twitch.tv ChatBot
This is a chatbot that processes chat messages. It includes a chat logger and commands that
return a pre-written message or execute a function with parameters specified in the message.

## Description of Files

* **Run.py:**        The main logic for the bot. Run this file to use the bot.
* **Settings.py:**   Contains vital settings variables relating to connectivity and authentication.
* **Commands.py:**   The module that contains the lists of commands and miscellaneous command functions.
* **Sounds.py:**     A module of commands allowing the chat to play personal sounds using custom commands.
* **Quotebot.py:**   A module of commands allowing the chat to add, save, and reference custom quotes in chat.
* **Logger.py:**     Automatically logs chat activity and saves it to "/documents/logs/log-<month>-<day>-<year>.txt".

## Note
In order for the code to run correctly, Settings.py needs to be completed with the appropriate information. This includes PASS, NICK, CHANNEL, and OWNER.
