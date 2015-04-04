### 3. Copy input files to HDFS (stage-in)
$HADOOP_HOME/bin/hadoop dfs -rmr output
$HADOOP_HOME/bin/hadoop dfs -rmr queryIDFOutput
$HADOOP_HOME/bin/hadoop dfs -rmr moldOutput
$HADOOP_HOME/bin/hadoop dfs -rmr contentOutput

$HADOOP_HOME/bin/hadoop dfs -rm rankResult.txt
make clean
make
jar cf booleanRetrieval.jar *.java *.class
### 4. Run MapReduce job
$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver
$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver3
while [ ! -f ./output/part-r-00000 ]
do
  sleep 1
done
$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver2

while [ ! -f ./moldOutput/part-r-00000 ]
do
  sleep 1
done

while [ ! -f ./queryIDFOutput/part-r-00000 ]
do
  sleep 1
done

java PageRank
while [ ! -f ./rankResult.txt ]
do
  sleep 1
done
$HADOOP_HOME/bin/hadoop jar booleanRetrieval.jar Driver4
