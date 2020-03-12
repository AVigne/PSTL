#!/bin/bash
for i in `seq 1 $3`;
do
	java -jar PSTL.jar $1 $2 &>tests/test$i.c
	mopsa-c tests/test$i.c
done
