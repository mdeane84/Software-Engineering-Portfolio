import socket
import string
import random
import quotebot
import requests
from datetime import datetime
from Commands import strPrivCommands, strPubCommands, functPrivCommands, functPubCommands
from Settings import HOST, PORT, PASS, NICK, CHANNEL, OWNER, MODS, WHITELIST, BLACKLIST

sock = socket.socket()
sock.connect((HOST, PORT))
sock.send(("PASS " + PASS + "\n" +
           "NICK " + NICK + "\n" +
           "JOIN #" + CHANNEL + "\n").encode())

#====== Joins the chat and initializes the bot ======================
def joinchat():
    Loading = True
    while Loading:
        readbuffer_join = sock.recv(1024)
        readbuffer_join = readbuffer_join.decode()
        for line in readbuffer_join.split("\n")[0:-1]:
            print(line)
            Loading = loadingComplete(line)

def loadingComplete(line):
    if("End of /NAMES list" in line):
        loadPoints()
        print("\n" + NICK + " has joined the chat!")
        log("\n======================================")
        log(NICK + " has joined the chat! ~ " + str(datetime.now().hour) + ":" + str(datetime.now().minute) + ":" + str(datetime.now().second))
        log("======================================")
        sendMessage(sock, "No need to fear, " + NICK + " is here!")
        return False
    else:
        return True
#=====================================================================

#====== Communication Methods ========================================

def log(message):
    print(message)

    date = str(datetime.now().month) +"-"+ str(datetime.now().day) +"-"+ str(datetime.now().year)
    file = open("documents/logs/log-" + date + ".txt", "a+")
    file.write("\n"+message)
    file.close()

# sends a message through the socket
def sendMessage(sock, message):
    messageTemp = "PRIVMSG #" + CHANNEL + " :" + message
    log("Bot: " + message)
    sock.send((messageTemp + "\n").encode())

# returns the user who sent the given message
def getUser(line):
    separate = line.split(":", 2)
    user = separate[1].split("!", 1)[0]
    return user

# returns the message of the string
def getMessage(line):
    try:
        message = (line.split(":", 2)[2])
    except:
        message = ""
    return message

# returns True if message is sent from twitch
def Console(line):
    if "PRIVMSG" in line:
        return False
    else:
        return True

joinchat()
#=====================================================================

#====== Main Loop ====================================================

# Loops to receive commands
while True:

    readbuffer = ""
    try:
        readbuffer = sock.recv(1024).decode()
    except:
        readbuffer = ""
    for line in readbuffer.split("\r\n"):
        if line == "":
             continue
        else:
            user = getUser(line)
            message = getMessage(line)
            if "PING" not in line:
                log(user + ": " + message)

        # Pings twitch to prevent timeout
        if "PING" in line and Console(line):
            msg = "PONG :tmi.twitch.tv\r\n".encode()
            sock.send(msg)
            print(msg)
            continue

        # Adds a quote to quotes.txt
        if "!addquote" in message:
            if quotebot.addQuote(message):
                sendMessage(sock, "Quote added!")
            else:
                sendMessage(sock, "Improper usage.")

        # Case no args: sends a random quote from quotes.txt
        #Case arg: sends the quot of the given index from quotes.txt
        if "!quote" in message:
            if message.strip() == "!quote":
                sendMessage(sock, quotebot.getRandQuote())
            else:
                try:
                    quote = quotebot.getQuote(message)
                except:
                    sendMessage(sock, "Quote doesn't exist")
                    continue
                sendMessage(sock, quote)

        # Generic commands
        if "!" in message and user not in BLACKLIST:
            args = [user, message]

        # Public Commands
            #   STRING
            if message in strPubCommands:
                sendMessage(sock, strPubCommands[message])
                continue
            #   FUNCTION
            if message.split(" ", 1)[0] in functPubCommands:
                sendMessage(sock, functPubCommands[message.split(" ", 1)[0]](args))
                continue

        # Private Commands
            if user in MODS or user in WHITELIST:
            #   STRING
                if message in strPrivCommands:
                    sendMessage(sock, strPrivCommands[message])
                    continue
            #   FUNCTION
                if message.split(" ", 1)[0] in functPrivCommands:
                    sendMessage(sock, functPrivCommands[message](args))
                    continue
