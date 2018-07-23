package load

import io.gatling.core.Predef._
import load.base.{Config, Log, SparkStats}

class SparkSimulation extends Simulation  with Log with Config {

  val scn = scenario("max batch time").forever {
    exec(
      SparkStats.getSparkContextUrl,
      SparkStats.getApplicationId,
      SparkStats.checkMaxProcessingTime
    )
  }

  val load = scn.inject(rampUsers(1) over (1))

  setUp(load)
    .protocols(SparkStats.protocol)
    .maxDuration(config.loadProfile.maxDurationSeconds)
    .assertions(
    forAll.failedRequests.count.is(0),
    forAll.responseTime.percentile4.lte(config.loadProfile.expectedResponseTimeMsec)
  )

}