#include <LiquidCrystal.h>
 
int led[4] = {13,31,33,35};
boolean ledStatus[4] = {LOW,LOW,LOW,LOW};

int lastTime[] = {0,0,0,0};

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
const int analogInPin[] = {A0,A1,A2,A3,A4,A5};
float sensorValue[6] = {0,0,0,0,0,0};
float voltageValue[6] = {0,0,0,0,0,0};
char inbyte = 0;

void setup() {
  Serial.begin(9600);
  lcd.begin(20, 4);

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
  if (Serial.available() > 0){
    inbyte = Serial.read();
    
    switch (inbyte){
      case '0':
        digitalWrite_2(0, LOW); 
        break;

      case '1':
        digitalWrite_2(0, HIGH);
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
 
  delay(1000);
}
 
void readSensors()
{
  for(int p=0; p<6; p++){
    sensorValue[p] = analogRead(analogInPin[p]);
    if(p==0){
      if(sensorValue[p] > 700){
        if(ledStatus[1] !=HIGH && (millis()-lastTime[1])>5000 ) 
          digitalWrite_2(1, HIGH);
          
      }else{
        if(ledStatus[1] !=LOW)
          digitalWrite_2(1, LOW);
          
      }
      
    }
  }
}
void sendAndroidValues(){
  Serial.print('#');


  for(int j=0; j<4; j++){
    Serial.print(ledStatus[j]==LOW?'0':'1');
    Serial.print('+');
  }
  
 
  for(int k=0; k<6; k++){
    Serial.print(voltageValue[k]);
    Serial.print('+');
    
  }
 Serial.print('~');
 Serial.println();
 delay(500);        
}
 
void printLCD(){
   
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
