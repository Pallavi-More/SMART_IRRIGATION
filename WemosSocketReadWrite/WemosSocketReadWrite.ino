#include <ESP8266WiFi.h>
#include <WiFiClient.h>
#include <stdlib.h>

const char ssid[] = "gas";
const char password[] = "12345678";
const char serverip[] = "192.168.43.113";
const int serverport = 3366;


WiFiClient client;


void setup() {

  // put your setup code here, to run once:
  Serial.begin(9600);

  // Put some delay so that we can start serial Monitor...
  delay(4000);

  //Connect to WiFi
  while (setupWiFi() == false);
}

void loop() {
  String reply = connectAndSendDataToServer("hi");
  delay(1000);
}

bool setupWiFi()
{
  // Connect to the Wi-Fi access point
  Serial.print("Connecting to Wifi:");
  Serial.println(ssid);
  WiFi.begin(ssid, password);
  int i = 0;
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");

    if (i++ == 50)
    {
      Serial.println();
      Serial.print("Connection to Wifi ");
      Serial.print(ssid);
      Serial.println(" Failed");
      return false;
    }
  }
  Serial.println("WiFi connection Success.");
  Serial.print("My IP address: ");
  Serial.println(WiFi.localIP());

  return true;
}

String connectAndSendDataToServer(String dataToSend) 
{
  Serial.print("Connecting to server at: ");
  Serial.println(serverip);
  if (!client.connect(serverip, serverport))
  {
    Serial.println("connection failed");
    return "-1";
  }
  Serial.println("Connected to host!!!");
  delay(1000);
  
  Serial.print("Sending ");
  Serial.print(dataToSend);
  Serial.println(" to server...");
  
  client.print(dataToSend);
  delay(1000);

  Serial.println("Waiting for reply from server...");
  
  // Read all the lines of the reply from server and print them to Serial
  String reply = "";
  while (client.available()) {
    reply = client.readStringUntil('\r');
    //int data = client.read();
    //reply = String(data, HEX);
    //Serial.print(client.read(), HEX);
    //Serial.print(" ");
  }
  Serial.print("Got '");
  Serial.print(reply);
  Serial.println("' From Server.");
  delay(1000);

  Serial.println("Closing connection with server...");
  client.stop();
  Serial.println("Connection closed!!!");
  Serial.println();
  return reply;
}

