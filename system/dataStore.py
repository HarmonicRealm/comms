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

import socket, sys, time, json, sqlite3
from arduinoPinger import ping

def receive_from_arduino_pinger(s, port):
        buf, address = s.recvfrom(1024)
        buf = buf.decode('utf-8')
        buf = buf.replace("\'", "\"")
        results = json.loads(buf)
        
        l = results['l']
        t = results['t']
        p = results['p']
        y = results['y']

        db = sqlite3.connect('../webserver/location.db')
        cursor = db.cursor()
        
        sqlite3_query_temperatures = "INSERT INTO 'temperatures' ('0.00m', '0.25m', '0.50m', '0.75m') VALUES ({}, {}, {}, {});".format(t["0.00m"], t["0.25m"], t["0.50m"], t["0.75m"])
        sqlite3_query_t_id = "SELECT MAX(ttemperature_id) FROM 'temperatures'"
        cursor.execute(sqlite3_query_temperatures)
        t_id = cursor.execute(sqlite3_query_t_id).fetchall()[0][0]

        sqlite3_query = "INSERT INTO 'location_values' ('location_id', 'tdate', 'ttime', 'tph', 'ttemperature_id', 'tturbidity') VALUES ({}, date('now'), time('now'), {}, {}, {});".format(l, p, t_id, y)
        cursor.execute(sqlite3_query)
        db.commit()

if __name__ == "__main__":
        s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        listening_on = ('localhost', 300)
        s.bind(listening_on)

        while True:
                print("Waiting for message from arduino_pinger")
                collected_values = receive_from_arduino_pinger(s, 100)
                ping(s, 200, 'success')

