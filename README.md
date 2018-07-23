# kaaiot-qa-perf
Gatling performance test suite

## How to run locally

install [sbt](http://www.scala-sbt.org/download.html) tool

run with default settings:
```
$ sbt -Djsse.enableSNIExtension=false clean 'gatling:testOnly load.SparkSimulation'
```

run with parameters:

```
$ export GATLING_SPARK_HOST=spark.host.com
$ export GATLING_SPARK_PORT=8888
$ sbt -Djsse.enableSNIExtension=false clean 'gatling:testOnly load.SparkSimulation'
```
