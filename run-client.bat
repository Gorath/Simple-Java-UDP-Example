@echo off
echo 	========   Java batch run for UDP Client   ========
echo 	========      (c) Gareth Jones 2011        ========
cd bin
echo on
java -Djava.security.policy=../openall.policy client/UDPClient %1 %2 %3
@echo off
cd ../
echo on