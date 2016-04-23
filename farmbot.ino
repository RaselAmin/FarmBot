#include <LiquidCrystal.h>
 
int led[4] = {13,31,33,35};
boolean ledStatus[4] = {LOW,LOW,LOW,LOW};

int lastTime[] = {0,0,0,0};

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
// Pins used for inputs and outputs********************************************************
const int analogInPin[] = {A0,A1,A2,A3,A4,A5};// Analog input pins

//Arrays for the 4 inputs**********************************************
float sensorValue[6] = {0,0,0,0,0,0};
float voltageValue[6] = {0,0,0,0,0,0};
 
//Char used for reading in Serial characters
char inbyte = 0;
//*******************************************************************************************
 
void setup() {
  // initialise serial communications at 9600 bps:
  Serial.begin(9600);
  lcd.begin(20, 4); //change to 16, 2 for smaller 16x2 screens

  for(int i=0; i<4; i++){
    pinMode(led[i], OUTPUT);
    digitalWrite_2(i, ledStatus[i]);
  }
}

void digitalWrite_2(int i, boolean st){
  lastTime[i] = millis();
  digitalWrite(led[i], (ledStatus[i]=st));
}
 
void loop() {
  readSensors();
  getVoltageValue();
  printLCD();
  sendAndroidValues();
  //when serial values have been received this will be true
  if (Serial.available() > 0){
    inbyte = Serial.read();
    
    switch (inbyte){
      case '0':
        digitalWrite_2(0, LOW); //LED off
        break;

      case '1':
        digitalWrite_2(0, HIGH); //LED on
        break;

      case '2':
        digitalWrite_2(1, LOW);
        break;

      case '3':
        digitalWrite_2(1, HIGH);
        break;

      case '4':
        digitalWrite_2(2, LOW);
        break;

      case '5':
        digitalWrite_2(2, HIGH);
        break;

      case '6':
        digitalWrite_2(3, LOW);
        break;

      case '7':
        digitalWrite_2(3, HIGH);
        break;
    }
  }
  //delay by 2s. Meaning we will be sent values every 2s approx
  //also means that it can take up to 2 seconds to change LED state
  delay(1000);
}
 
void readSensors()
{
  // read the analog in value to the sensor array
  for(int p=0; p<6; p++){
    sensorValue[p] = analogRead(analogInPin[p]);
    if(p==0){
      //Moisture vs Irrigation:
      if(sensorValue[p] > 700){
        if(ledStatus[1] !=HIGH && (millis()-lastTime[1])>5000 ) //do not update within 5 seconds;
          digitalWrite_2(1, HIGH);
          
      }else{
        if(ledStatus[1] !=LOW)
          digitalWrite_2(1, LOW);
          
      }
      
    }
  }
}

//sends the values from the sensor over serial to BT module
void sendAndroidValues(){
  //puts # before the values so our app knows what to do with the data
  Serial.print('#');


  for(int j=0; j<4; j++){
    Serial.print(ledStatus[j]==LOW?'0':'1');
    Serial.print('+');
  }
  
  //for loop cycles through 4 sensors and sends values via serial
  for(int k=0; k<6; k++){
    Serial.print(voltageValue[k]);
    Serial.print('+');
    //technically not needed but I prefer to break up data values
    //so they are easier to see when debugging
  }
 Serial.print('~'); //used as an end of transmission character - used in app for string length
 Serial.println();
 delay(500);        //added a delay to eliminate missed transmissions
}
 
void printLCD(){
   //change 4 to 2 if using small screen
   lcd.setCursor(0,0);
    lcd.print("      FarmBot");
    lcd.setCursor(0, 1);
    lcd.print("Mo:");
    lcd.print(voltageValue[0]);
    lcd.setCursor(10, 1);
    lcd.print("Te:");
    lcd.print(voltageValue[1]);
    lcd.setCursor(0, 2);
    lcd.print("Hu:");
    lcd.print(voltageValue[2]);
    lcd.setCursor(10, 2);
    lcd.print("ph:");
    lcd.print(voltageValue[3]);
    lcd.setCursor(0, 3);
    lcd.print("Me:");
    lcd.print(voltageValue[4]);
    lcd.setCursor(10, 3);
    lcd.print("co:");
    lcd.print(voltageValue[5]);
   
  }
void getVoltageValue(){
  for (int x = 0; x < 6; x++){
    voltageValue[0] = sensorValue[0];
    voltageValue[1] = sensorValue[1];
    voltageValue[2] = sensorValue[2];
    voltageValue[3] = sensorValue[3];
    voltageValue[4] = sensorValue[4];
    voltageValue[5] = sensorValue[5];
  }
}
