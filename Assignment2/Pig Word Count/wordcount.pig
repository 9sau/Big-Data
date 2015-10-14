A = load './input.txt' as line;
Z = FOREACH A GENERATE REPLACE(line,'([^a-zA-Z\\s]+)',' '); 
B = foreach Z generate flatten(TOKENIZE((chararray)$0)) as word;
X = foreach B generate LOWER(word) as word;
C = group X by word;
E = filter C by ($0 matches 'java' or $0 matches 'dec' or $0 matches 'hackathon' or $0 matches 'chicago');
D = foreach E generate group, COUNT(X) as value;
R1 = foreach D generate $0 as col1:chararray, $1 as col2:int;
R2 = LOAD './temp.txt' USING PigStorage (' ') as (col1:chararray, col2:int);
R3 = Union R1, R2;
R4 = distinct R3;
R5 = foreach R4 generate $0 as col1:chararray, $1 as col2:int; 
R6 = group R5 by col1;
R7 = foreach R6 generate $0,SUM(R5.col2);
R8 = ORDER R7 by group ASC;
store R8 into './wordcount';

