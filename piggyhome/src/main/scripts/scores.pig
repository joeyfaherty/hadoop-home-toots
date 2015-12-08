-- register udf jar
REGISTER ../lib/examscore-parser*.jar

-- load records using a defined schema
records = LOAD 'examScores.csv' using PigStorage(',') AS (fname:chararray, lname:chararray, subject:chararray, examScore:int) ;
--dump records;

-- use java udf to filter records
filter_records = FILTER records BY com.emc.IsAPassingGrade(examScore);
--dump filtered_records;

-- group by
grouped_records = GROUP filter_records BY subject;

-- store result
STORE grouped_records into '/user/devuser/tmptest’;