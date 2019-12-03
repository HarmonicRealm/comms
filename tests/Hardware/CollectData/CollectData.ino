#include <OneWire.h>
#include <DallasTemperature.h>
#include <Stepper.h> //for the motor

#define ONE_WIRE_BUS 5  // digital pin 5 for temperature sensor
#define SENSOR A0      //analog pin A0 for turbidity sensor
#define STEPS 2048

Stepper stepper(STEPS, 8, 10, 9, 11); //motor using digital pins 8,9,10,11

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
float data[4]; //array containing all the sensor values

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
const int analogInPin = A1;
float avgValue = 0;
float volts, pHVolt, pHValue;
float totalVolts = 0;

void setup()
{

  Serial.begin(9600);

  if (Serial.available()) {    // RPi to Arduino serial communication

    if (Serial.read() - '0' == 1) {  //if Rpi sends 1(pings the arduino) then only start collecting the data

      pinMode(SENSOR, INPUT);
      sensors.begin();

      CollectTurbidity();
    }
    // 0.25 per call
    runMotor(6);
    CollectTemperature();
    // Set in the array -> for i array[depth] = i * 0.25, array[temperature] = temperature
    CollectpH();



  }
}


void runMotor(int speed) {
  stepper.setSpeed(abs(speed));

  if (speed > 0) {
    stepper.step(STEPS);
  } else {
    stepper.step(-STEPS);
  }
  delay(1000);
}

void CollectTemperature() {
  for (int i = 0; i < 50; i++) {
    sensors.requestTemperatures();  //start fetching temperature
    Celcius = sensors.getTempCByIndex(0);  //convert the temperature to degree celsius
    Fahrenheit = sensors.toFahrenheit(Celcius);  // convert the tempertaure to fahrenheit
    totalC += Celcius;
    totalF += Fahrenheit;
    delay(5);
  }

  Serial.print("Temperature: ");
  Serial.print(totalC / 50);
  Serial.print("*C  ");
  Serial.print(totalF / 50);
  Serial.println("*F  ");
}

void CollectpH() {
  totalVolts = 0;
  for (int i = 0; i < 10; i++) { //10 samples for pH value
    Serial.println(analogRead(SENSOR));
    volts = (5.0 / 1024.0) * analogRead(SENSOR);
    totalVolts += volts;
  }
  Serial.println(totalVolts);
  pHVolt = totalVolts / 10;
  Serial.println(pHVolt);
  //pHValue = -5.70 * pHVolt + 21.34; //formuala to convert voltage to a pH value
  pHValue = 3.5* pHVolt;
 // Serial.println(7-pHVolt/57.15);
  //pHValue = 7 - (pHVolt/57.15);
  Serial.println(pHValue);
}

void CollectTurbidity() {
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

  Serial.println("Voltage=" + String(voltAvg) + " V Turbidity=" + String(turbidityAvg) + " NTU");

  //delay(500);
  //} else
}
