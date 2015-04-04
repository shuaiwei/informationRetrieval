export HADOOP_HOME=/home/wei6/software/hadoop-1.2.1
export HADOOP_CORE_CLASSPATH=/home/wei6/software/hadoop-1.2.1/hadoop-core-1.2.1.jar
export CLASSPATH=$CLASSPATH:$HADOOP_CORE_CLASSPATH:
export HADOOP_CLASSPATH=$HADOOP_CLASSPATH:./
export PATH=$JAVA_HOME/bin:$PATH
rm -rf output
rm -rf firstOutput
rm -rf secondOutput
make clean
make
jar cf booleanRetrieval.jar *.java *.class
qsub runHadoop.pbs