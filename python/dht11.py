import os

import sys
from datetime import *
import time
import board
import adafruit_dht
import paho.mqtt.client as mqtt
import json

def on_connect(client, userdata, flags, rc):
    print('received with code %d' % (rc))
    
def on_publish(client, userdata, mid):
    print('mid: '+str(mid))
    
# Data capture and upload interval in seconds. Less interval will eventually hang the DHT22.
INTERVAL=5

# sensor_data = {'temperature': 0, 'humidity': 0}

#Initial the dht device, with data pin connected to:
dhtDevice = adafruit_dht.DHT11(board.D17)
    
client = mqtt.Client()

# Set access token
client.username_pw_set("markglass", "Gretna12569")

# Connect to ThingsBoard using default MQTT port and 60 seconds keepalive interval
# client.connect("68157417d8a24f0f86e5b3b271996a93.s1.eu.hivemq.cloud", 8883)

client.loop_start()

try:
    while True:
       now = datetime.now().strftime("%d-%b-%y %H:%M:%S")
       try:
           my_json_string = json.dumps({"time": now, "tempC": 21, "humidity": 45})
           client.publish("dht11", payload=my_json_string, qos=0, retain=False)
           print(my_json_string)
       except Exception as e1:
           print("{} exception {}".format(now, e1.args[0]))
       except RuntimeError as error:
         # Errors happen fairly often, DHT's are hard to read, just keep going
         print(error.args[0])
         client.disconnect();

       time.sleep(5.0)     
except KeyboardInterrupt:
    pass

client.loop_stop()
client.disconnect()
