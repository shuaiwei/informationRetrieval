### 3. Copy input files to HDFS (stage-in)
$HADOOP_HOME/bin/hadoop dfs -rmr output
$HADOOP_HOME/bin/hadoop dfs -rmr secondOutput
$HADOOP_HOME/bin/hadoop dfs -rmr relevantDocOutput
$HADOOP_HOME/bin/hadoop dfs -rm top20RankResult.txt

make clean
make
jar cf booleanRetrieval.jar *.java *.class

$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver

while [ ! -f ./output/part-r-00000 ]
do
  sleep 1
done

$HADOOP_HOME/bin/hadoop fs -cat output/part-r-00000 | sort -n -k2 -r | head -n20 >> top20RankResult.txt
