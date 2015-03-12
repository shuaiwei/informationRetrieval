export HADOOP_HOME=/Users/88wuji/Documents/hadoop-1.2.1
rm -rf output
rm -rf firstOutput
rm -rf secondOutput
make
jar cf booleanRetrieval.jar *.java *.class
$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver
