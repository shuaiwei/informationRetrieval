export HADOOP_HOME=/Users/88wuji/Documents/hadoop-1.2.1
rm -rf output
make clean
make
jar cf booleanRetrieval.jar *.java *.class
$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver
