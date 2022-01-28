##
## build jar with dependences
##

mvn compiler:compile
mvn jar:jar
mvn assembly:single

##
## run producer code
##

java -cp target/kafka1-0.0.1-SNAPSHOT-jar-with-dependencies.jar my.kafka1.ProducerDemo

java -cp target/kafka1-0.0.1-SNAPSHOT-jar-with-dependencies.jar my.kafka1.ProducerDemoKeys

java -cp target/kafka1-0.0.1-SNAPSHOT-jar-with-dependencies.jar my.kafka1.ProducerDemoLoop topic1 192.168.56.35 10 study2

##
## run consumer code
##

java -cp target/kafka1-0.0.1-SNAPSHOT-jar-with-dependencies.jar my.kafka1.ConsumerDemo
