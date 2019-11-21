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

import socket, sys, time, random, json, serial
import RPi.GPIO as GPIO
from arduinoPinger import ping  

ser=serial.Serial("/dev/ttyACM0",9600)

def receive_from_arduino_pinger(s, port):

    buf, address = s.recvfrom(port)
    
    # write to arduino to start collecting the data
    ser.write(b'1')
        
    line = ''
    
    # using serial, read from the serial port
    while line == '':
        if (ser.in_waiting > 0):
            line = ser.readline()
        
    # fake_data = json.dumps(fakeTheData())
    print(line)

def fakeTheData():
    return {
        "location": random.choice([1, 2, 3]),
        "depth": random.choice([0, 25, 50, 75, 100]),
        "temperature": random.randint(15, 20),
        "ph": random.randint(0, 14),
        "turbidity": random.randint(0, 3000),
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
