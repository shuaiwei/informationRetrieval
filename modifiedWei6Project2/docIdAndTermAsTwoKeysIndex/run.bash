$HADOOP_HOME/bin/hadoop dfs -rmr firstOutput
$HADOOP_HOME/bin/hadoop dfs -rmr termIndexOutput
$HADOOP_HOME/bin/hadoop dfs -rmr docIndexOutput

make clean
make
jar cf booleanRetrieval.jar *.java *.class

$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver
