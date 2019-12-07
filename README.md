# comms

## setup
1. rn `pip3 install -r requirements.txt`
2. connect your arduino to the system running the scripts and load Data_Collect.ino onto it
. open three terminal windows/tabs and run (in order):
	1. `sudo python3 dataStore.py`
	2. `sudo python3 dataCollector.py`
	3. `sudo python3 arduinoPinger.py`

expected outcome: arduinoPinger.py starts messaging dataCollector.py. dataCollector sends a serial message to the arduino, who reads it and starts collecting values from the sensors. The arduino sends the values back in the form of a 2D array, where dataCollector.py turns it into a dictionary, and sends it back to the arduinoPinger.py via UDP. arduinoPinger.py forwards it to dataStore.py, who stores the temperature and associated values into tables.
3. download four pages UI to your Android studio(.xml), and then download the java classes and run them in the Android studio. 
