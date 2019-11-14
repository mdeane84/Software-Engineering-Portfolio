import random
from datetime import datetime

# writes a given quote at the end of quotes.txt
#"quote" -user [mm/dd/yyyy]
def addQuote(message):
    file = open("documents/quotes.txt", 'a+')
    try:
           quote = message.split(" ", 1)[1]
    except:
    	file.close()
    	return False
    lines = file.readlines()
    num = len(lines)
    date = str(datetime.now().month) +"/"+ str(datetime.now().day) +"/"+ str(datetime.now().year)
    file.write("\n" + quote + " [" + date + "]")
    file.close()
    return True

# returns a given quote from quotes.txt
# Quote #: "quote" -user [mm/dd/yyyy]
def getQuote(message):
	file = open("documents/quotes.txt", 'r+')
	position = message.split(" ", 1)[1]
	position = int(position)
	quotes = file.readlines()
	file.close()
	string = "Quote " + str(position) + ": " + quotes[position]
	return string

# returns a random quote from quotes.txt
# Quote #: "quote" -user [mm/dd/yyyy]
def getRandQuote():
	file = open("documents/quotes.txt", 'r+')
	quotes = file.readlines()
	position = random.randint(0, len(quotes)-1)
	file.close()
	string = "Quote " + str(position) + ": " + quotes[position]
	return string
