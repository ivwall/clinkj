# CW - Chain Walk
Initial Goals:
  Find my btc addresses
  List all addresses
  Map the utxo movement



chain links java - blockchain addresses and transactions

Ubuntu 20.04.3 LTS

sudo update-alternatives --config java
  Selection    Path                                         Priority   Status
------------------------------------------------------------
* 0            /usr/lib/jvm/java-17-openjdk-amd64/bin/java   1711      auto mode
  1            /usr/lib/jvm/java-11-openjdk-amd64/bin/java   1111      manual mode
  2            /usr/lib/jvm/java-17-openjdk-amd64/bin/java   1711      manual mode

Apache Maven 3.6.3

dateUtils offers a working POM.xml that creates a "full" jar

cd clinkj/dateUtils

mvn package

java -jar target/dateutils-jar-with-dependencies.jar 
