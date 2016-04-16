
#include<LiquidCrystal.h>
LiquidCrystal lcd(12,11,5,4,3,2);

const int analogPin = A0;  
const int analogPin1 = A5;
const int ledPin = 13;
const int ledPin1 = 10;
const int threshold = 800;
void setup() {
  pinMode(ledPin, OUTPUT);
  pinMode(ledPin1, OUTPUT);
  lcd.begin(16,2);
  delay(1000);
}
void loop() {
    lcd.clear();
    
lcd.print("City University");
lcd.setCursor(0,1);
lcd.print("Mois:");
  int analogValue = analogRead(analogPin);
  lcd.print(analogValue);
  int analogValue1=analogRead(analogPin1)/20;
  lcd.print("Temp:");
  lcd.print(analogValue1);
  if (analogValue > threshold) {
    digitalWrite(ledPin, HIGH);
    digitalWrite(ledPin1, HIGH);
   
  } 
  else {
    digitalWrite(ledPin,LOW);
   digitalWrite(ledPin1, LOW);
  }
  
  delay(15000);
}

