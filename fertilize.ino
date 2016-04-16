int led1 = 13;
int temp=A0;
int limit=35;
int state=LOW;
void setup() {                
  pinMode(led1, OUTPUT); 
  Serial.begin(9600);
}

void loop() {
  float sensor = analogRead(temp)/20;
  Serial.print("Temperature:");
  Serial.print(sensor);
    if(sensor>limit){
    state=!state;
    digitalWrite(led1,state);
    }
  delay(30000); 
}
