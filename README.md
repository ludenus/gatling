# kaaiot-qa-perf
Gatling performance test suite

## Before you begin
Checkout [Gatling docs](https://gatling.io/documentation/)

## How to run locally

install sbt tool [manually](http://www.scala-sbt.org/download.html)
or via [sdkman](https://sdkman.io/install)
```
$ sdk install sbt 1.1.6
```

run perf simulation with default settings:
```
$ sbt -Djsse.enableSNIExtension=false clean 'gatling:testOnly load.SparkSimulation'
```

run perf simulation with specific parameters:

```
$ export GATLING_SPARK_HOST=spark.host.com
$ export GATLING_SPARK_PORT=8888
$ sbt -Djsse.enableSNIExtension=false clean 'gatling:testOnly load.SparkSimulation'
```
