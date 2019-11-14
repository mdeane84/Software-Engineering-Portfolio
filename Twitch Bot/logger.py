from datetime import datetime

def log(message):
    print(message)

    date = str(datetime.now().month) +"-"+ str(datetime.now().day) +"-"+ str(datetime.now().year)
    file = open("documents/logs/log-" + date, a+)
    file.write(message)
    file.close()
