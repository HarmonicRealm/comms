#include <OneWire.h>
#include <DallasTemperature.h>
#include <Stepper.h> //for the motor

#define ONE_WIRE_BUS 5  // digital pin 5 for temperature sensor
#define SENSOR A0      //analog pin A0 for turbidity sensor
#define SENSORPH A1
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
//const int analogInPin = A1;
float avgValue = 0;
float volts, pHVolt, pHValue;
float totalVolts;

void setup()
{

  Serial.begin(9600);
}

void loop(){
  if (Serial.available()) {    // RPi to Arduino serial communication

   if (Serial.read() - '0' == 1) {  //if Rpi sends 1(pings the arduino) then only start collecting the data
      pinMode(SENSOR, INPUT);
      sensors.begin();
      
   
    
    // Call CollectTemperature before moving the motor to collect a 0m.
    data[1][0] = CollectTemperature();
    data[0][0] = 0.00;
    // Run a for loop for each runMotor and CollectTemperature call
    for (int i = 1; i < 4; i++) {
      // Run motor at maxspeed to move down 0.25m 
      runMotor(10);
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
   }
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

float CollectTemperature() {
  totalC = 0;
  totalF = 0;
  
  for (int i = 0; i < 50; i++) {
    sensors.requestTemperatures();  //start fetching temperature
    Celcius = sensors.getTempCByIndex(0);  //convert the temperature to degree celsius
    Fahrenheit = sensors.toFahrenheit(Celcius);  // convert the tempertaure to fahrenheit
    totalC += Celcius;
    //totalF += Fahrenheit;
    delay(5);
  }

  return totalC / 50;
}

float CollectpH() {
  totalVolts = 0;
  for (int i = 0; i < 10; i++) { //10 samples for pH value
    volts = (5.0 / 1024.0) * analogRead(SENSORPH);
    totalVolts += volts;
    delay(10);
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
    delay(5);

  }

  voltAvg = totalVoltage / 200;
  turbidityAvg = totalTurbidity / 200;

  // if ((voltAvg >= 1.5) & (turbidityAvg >= 0)) {

  //delay(500);
  //} else
  return turbidityAvg;
}
