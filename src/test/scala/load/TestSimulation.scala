package load

import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, responseTimeInMillis, status}
import load.base.{Config, Log}

class TestSimulation extends Simulation with Log with Config {

  val httpProtocol = http
    .baseURL(s"http://localhost:8111")
    .userAgentHeader("Gatling Performance Test")

  val csvFeeder = csv("src/test/resources/id.csv").random

  val scn = scenario("Test POST request")
    .feed(csvFeeder)
    .exec(http("Test POST request")
      .post("http://localhost:8111/ping/${id}")
      .header("Content-type", "application/json")
      .body(StringBody("""{"id":"${id}"}"""))
      .check(status is 200, responseTimeInMillis.lessThanOrEqual(1000))
    )

  val load = scn.inject(rampUsers(30) over (1))

  setUp(load)
    .protocols(httpProtocol)
    .maxDuration(10)
    .assertions(
      forAll.failedRequests.count.is(0),
      forAll.responseTime.percentile4.lte(1000)
    )

}
