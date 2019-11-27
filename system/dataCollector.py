# SYSC 3010 F 2019

# Arduino Pinger
# - sends to 10.0.0.1:100 (data collector)
# - receives on 10.0.0.1:200 (self)
# - sends to 10.0.0.1:300 (data store)

# Data Collector
# - receives on 10.0.0.1:100 (self)
# - sends to 10.0.0.1:200 (arduino pinger)

# Data Store
# - receives on 10.0.0.1:300 (self)
# - sends to 10.0.0.1:200 (arduino pinger)

import socket, sys, time, random, json
from arduinoPinger import ping  

ser = serial.Serial('/dev/tty/ACM0', 9600)

def receive_from_arduino_pinger(s, port):
    buf, address = s.recvfrom(port)
    print(buf.decode('utf-8'))

    ser.write(b'1')  
    line = ''

    while line == '':
        if (ser.in_waiting > 0):
            line = ser.readline()

    data = formatTheData(line)
    return data

def formatTheData(line):
    data = line.split(',')
    return {
        "location": 1,
        "temperature": {
            "0.00m": data[4],
            "0.25m": data[5],
            "0.50m": data[6],
            "0.75m": data[7],
            },
        "ph": data[8],
        "turbidity": data[12],
        }


if __name__ == "__main__":
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    listening_on = ('localhost', 100)
    s.bind(listening_on)

    while True:
        print("Waiting for value from arduino_pinger")
        collected_values = receive_from_arduino_pinger(s, 100)
        print("got "+collected_values+"\n")
        ping(s, 200, collected_values)
