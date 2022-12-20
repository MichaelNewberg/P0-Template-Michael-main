
httpCodes=$(grep Response newRollingFile.log | cut -f 1 -d , | cut -f 2 -d : | cut -b 3,4,5)
echo "$httpCodes" > httpRequest.log
httpRequestTotal=0
httpFailures=0
for code in $httpCodes
do
	((httpRequestTotal++))
	if [ $code -eq 500 ]
	then
		((httpFailures++))
	fi
done
echo $httpRequestTotal
echo $httpFailures
httpSuccess=$((httpRequestTotal - httpFailures))
result=$(awk "BEGIN {print $httpSuccess / $httpRequestTotal * 100; exit}")
echo "HTTP success rate: $result%."


httpLatency=$(grep Response newRollingFile.log | cut -f 2 -d , | cut -f 4 -d ' ')
echo "$httpLatency" > Latency.log
totalLatency=0
for code in $httpLatency
do
	totalLatency=$(awk "BEGIN{print $totalLatency + $code; exit}")
done
result2=$(awk "BEGIN {print $totalLatency / $httpRequestTotal; exit}")
echo "Average latency: $result2."
