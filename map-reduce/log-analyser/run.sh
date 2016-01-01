#!/bin/bash

# build project
mvn clean install

# delete output dir in HDFS
hadoop fs -rm -r /wc/output

# run job
hadoop jar target/log-analyser.jar com.home.mapreduce.ErrorParser /wc/input /wc/output

# print output file to console
hadoop fs -cat /wc/output/part-r-00000

