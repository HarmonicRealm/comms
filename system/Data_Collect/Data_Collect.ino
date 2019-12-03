#include <OneWire.h>
#include <DallasTemperature.h>
#include <Stepper.h> //for the motor

#define ONE_WIRE_BUS 5  // digital pin 5 for temperature sensor
#define SENSOR A0      //analog pin A0 for turbidity sensor
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
#define SENSOR_P A1
=======
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
#define STEPS 2048

Stepper stepper(STEPS, 8, 10, 9, 11); //motor using digital pins 8,9,10,11

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
float data[4][4]; //array containing all the sensor values

// Temperatur sensor variables
float Celcius = 0.0;
float Fahrenheit = 0.0;
float cavg, favg;
float totalC = 0.0;
float totalF = 0.0;

//Turbidity sensor variables
float totalVoltage = 0.0;
float totalTurbidity = 0.0;
float voltage, turbidity, voltAvg, turbidityAvg;

//PH sensor variables
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
//const int analogInPin = A1;
=======
const int analogInPin = A1;
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
float avgValue = 0;
float volts, pHVolt, pHValue;
float totalVolts = 0;

void setup()
{

  Serial.begin(9600);
}

void loop(){
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
  //if (Serial.available()) {    // RPi to Arduino serial communication

   //if (Serial.read() - '0' == 1) {  //if Rpi sends 1(pings the arduino) then only start collecting the data
=======
  if (Serial.available()) {    // RPi to Arduino serial communication

   if (Serial.read() - '0' == 1) {  //if Rpi sends 1(pings the arduino) then only start collecting the data
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
      pinMode(SENSOR, INPUT);
      sensors.begin();
      
   
    
    // Call CollectTemperature before moving the motor to collect a 0m.
    data[1][0] = CollectTemperature();
    data[0][0] = 0.00;
    // Run a for loop for each runMotor and CollectTemperature call
    for (int i = 1; i < 4; i++) {
      // Run motor at maxspeed to move down 0.25m 
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
      runMotor(7);
=======
      runMotor(10);
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
      // Put data values into the array
      //data[0][i - 1] = (i-1) * 0.25;
      data[0][i]=data[0][i-1] + 0.25;
      data[1][i] = CollectTemperature();
    }

    data[2][0] = CollectpH();
    data[3][0] = CollectTurbidity();

    // depths
    //    Serial.println(data[0][0]);
    //    Serial.println(data[0][1]);
    //    Serial.println(data[0][2]);
    //    Serial.println(data[0][3]);
    //    // corresponding temperatures
    //    Serial.println(data[1][0]);
    //    Serial.println(data[1][1]);
    //    Serial.println(data[1][2]);
    //    Serial.println(data[1][3]);
    //    // pH
    //    Serial.println(data[2][0]);
    //    // turbidity
    //    Serial.println(data[3][0]);


    for (int i = 0; i < 2; i++){
      for (int j = 0; j < 4; j++){
        Serial.print(data[i][j]);
        Serial.print(',');
      }
    }
    Serial.print(data[2][0]);
    Serial.print(',');
    Serial.print(data[3][0]);
    Serial.print(',');
    Serial.println();
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
   //}
  //}
=======
   }
  }
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
}


void runMotor(int speed) {
  stepper.setSpeed(abs(speed));

  if (speed > 0) {
    stepper.step(STEPS);
  } else {
    stepper.step(-STEPS);
  }
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
  //delay(1000);
=======
  delay(1000);
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
}

float CollectTemperature() {
  totalC = 0;
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
  //totalF = 0;
=======
  totalF = 0;
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
  
  for (int i = 0; i < 50; i++) {
    sensors.requestTemperatures();  //start fetching temperature
    Celcius = sensors.getTempCByIndex(0);  //convert the temperature to degree celsius
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
    //Fahrenheit = sensors.toFahrenheit(Celcius);  // convert the tempertaure to fahrenheit
    totalC += Celcius;
    //totalF += Fahrenheit;
=======
    Fahrenheit = sensors.toFahrenheit(Celcius);  // convert the tempertaure to fahrenheit
    totalC += Celcius;
    totalF += Fahrenheit;
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
    delay(5);
  }

  return totalC / 50;
}

float CollectpH() {
  totalVolts = 0;
  for (int i = 0; i < 10; i++) { //10 samples for pH value
<<<<<<< HEAD:system/Data_Collect/Data_Collect.ino
    volts = (5.0 / 1024.0) * analogRead(SENSOR_P);
=======
    volts = (5.0 / 1024.0) * analogRead(SENSOR);
>>>>>>> 05ad206d28fcef45a486c9018801af0e2512024d:system/Data_Collect.ino
    totalVolts += volts;
  }

  pHVolt = totalVolts / 10;
  pHValue = -5.70 * pHVolt + 21.34; //formuala to convert voltage to a pH value

  return pHValue;
}

float CollectTurbidity() {
  totalVoltage = 0;
  totalTurbidity = 0;
  for (int i = 0; i <= 200; i++) { // 200 samples taken for turbidity sensor values
    voltage = (5.0 / 1024.0) * analogRead(SENSOR);
    turbidity = (-1120.4 * (voltage + 0.3) * (voltage + 0.3)) + (5742.3 * (voltage + 0.3)) - 4352.9; //in NTU - convert the voltage to Nephelometric Turbidity unit.(0=clear; 3000= very turbid)
    totalVoltage += voltage;
    totalTurbidity += turbidity;
    //delay(5);

  }

  voltAvg = totalVoltage / 200;
  turbidityAvg = totalTurbidity / 200;

  // if ((voltAvg >= 1.5) & (turbidityAvg >= 0)) {

  //delay(500);
  //} else
  return turbidityAvg;
}
