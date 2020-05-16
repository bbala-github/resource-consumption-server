# resource-consumption-server
minimal java server which can be instructed to consume required CPU and memory

### Build
mvn package

### Run
Pick the fat jar from target folder.
java -Xmx<required-size>g -jar resource-consumption.jar server config.yml

### Invoke

`$ curl -X GET http://0.0.0.0:8080/resourceconsumption/memory?memInGiB=1`

Output: `{"name":"Hello Memory consumption done successfully for 1 GB"}`

`$ curl -X GET http://0.0.0.0:8080/resourceconsumption/cpu?cores=16`

Output: `{"name":"Hello CPU consumption started successfully for 16 cores"}`
