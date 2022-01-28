# interop
Repository for synchronizing aggregate data between Zimbabwe National Malaria Control Program(immis) and Zimbabwe Health Information System (nhis) 

# how to deploy

############1.1 CREATE DATABASE ################################
Create a non-privileged user called interop by invoking:
´´´
sudo -u postgres createuser -SDRP interop
´´´

Enter interop as password at the prompt. Create a database called interop by invoking:
´´´
sudo -u postgres createdb -O interop interop
´´´

Add the uuid extension to the created database by invoking:
```
sudo -u postgres psql interop


Enter the following command into the console:
´´´
CREATE EXTENSION "uuid-ossp";
´´´

Exit the console and return to your previous user with \q followed by exit.
############1.2 CREATE AND RUN EXECUTABLE JAR#################
´´´
sudo mkdir /etc/interop
sudo mkdir /etc/interop/config
cd /etc/interop && sudo git clone https://github.com/ITINordic/interop.git
sudo cp /etc/interop/interop/src/main/resources/application.properties /etc/interop/config
sudo vi /etc/interop/config/application.properties
cd /etc/interop/interop && sudo mvn package
sudo cp /etc/interop/interop/target/interop-0.0.1-SNAPSHOT.jar /etc/interop
cd /etc/interop && sudo nohup java -jar interop-0.0.1-SNAPSHOT.jar > ~/interop.log &
curl -L http://localhost:9090/interop
´´´
#############2.1 REDEPLOYMENT #################################
´´´
sudo netstat -peanut | grep 9090
sudo kill -9 processId
cd /etc/interop/interop && sudo git pull
cd /etc/interop/interop && sudo mvn package
sudo cp /etc/interop/interop/target/interop-0.0.1-SNAPSHOT.jar /etc/interop
cd /etc/interop && sudo nohup java -jar interop-0.0.1-SNAPSHOT.jar > ~/interop.log &
curl -L http://localhost:9090/interop
´´´

