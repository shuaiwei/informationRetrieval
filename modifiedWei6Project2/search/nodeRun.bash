### 3. Copy input files to HDFS (stage-in)
$HADOOP_HOME/bin/hadoop dfs -rmr output
$HADOOP_HOME/bin/hadoop dfs -rmr relevantDocOutput

make clean
make
jar cf booleanRetrieval.jar *.java *.class

$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver
$HADOOP_HOME/bin/hadoop fs -cat output/part-r-00000 | sort -n -k2 -r | head -n20
