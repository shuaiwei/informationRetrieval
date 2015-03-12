rm -rf output
make clean
make
jar cf booleanRetrieval.jar *.java *.class
/Users/88wuji/Documents/hadoop-1.2.1/bin/hadoop jar booleanRetrieval.jar Driver
cat output/part-r-00000