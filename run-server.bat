@echo off
echo 	========   Java batch run for UDP Server   ========
echo 	========      (c) Gareth Jones 2011        ========
cd bin
echo on
java -cp . -Djava.security.policy=../openall.policy server/UDPServer %1
@echo off 
cd ../
echo on