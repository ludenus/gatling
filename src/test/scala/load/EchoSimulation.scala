package load

import io.gatling.core.Predef._
import load.base.{Config, EchoSteps, Log}

class EchoSimulation extends Simulation with Log with Config {

  val scn = scenario("echo").forever {
    exec(EchoSteps.echo)
  }

  val load = scn.inject(rampUsers(10000) over (100))

  setUp(load)
    .protocols(EchoSteps.protocol)
    .maxDuration(100)
    .assertions(
      forAll.failedRequests.count.is(0),
      forAll.responseTime.percentile4.lte(config.loadProfile.expectedResponseTimeMsec)
    )

}
