import random
from Sounds import sounds
from playsound import playsound

#args = [user, message]

def eightball(args):
	file = open("documents/eightball.txt", "r+")
	response = file.readlines()
	position = random.randint(0, len(response)-1)
	response = response[position]
	file.close()
	return response

def hug(args):
	if (random.randint(0, 5) == 5):
		return "no"
	else:
		line = "/me gives " + str(args[0]) + " a big hug"
		return line

def coinflip(args):
	user = args[0]

	if (random.randint(0, 1) == 1):
		return "heads"
	else:
		return "tails"

def playSound(args):
	try:
		user = sounds[args[0]]
		sound = user[args[1].split(" ", 1)[1]]
	except:
		return ""
	playsound("documents/sounds/" + sound)
	return ""

strPrivCommands = {"!break"		:	"Going on break! Do the do and take care of the things and we will be right back."}

strPubCommands = {"!commands"	:	"Commands: !8ball, !hug, !coinflip, !break,!info",

				  "!info"		:	"I am a variety caster with no set schedule. Sometimes I am live, sometimes I am not. I play whatever I feel "
				  					"like playing so it will usually be something new every stream."}

functPrivCommands = {"!hug" : hug}

functPubCommands = {"!8ball"	:	eightball,
				 	"!hug"		:	hug,
				 	"!coinflip"	:	coinflip,
					"!sound"	: 	playSound}
