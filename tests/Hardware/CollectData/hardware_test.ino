#define SENSOR A0


void runTests(){
  /*int i;
  for(i = 1; i < 7; i++){
    runMotor(i);
  }
  for(i=0; i < 7; i++){
    runMotor(-i);
  }
  delay(1000);
  CollectTemperature();
  delay(2000);*/
  CollectTurbidity();
  delay(1000);
  //CollectpH();

}
