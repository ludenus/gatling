package load.base

import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, responseTimeInMillis, status}

object EchoSteps extends Object with Log with Config {

  log.info("{}", config)

  val expectedResponseTimeMsec = config.loadProfile.expectedResponseTimeMsec

  val protocol = http
    .baseURL(s"http://${config.echo.host}:${config.echo.port}")
    .userAgentHeader("Gatling Performance Test")

  val counter = new java.util.concurrent.atomic.AtomicLong(0)

  val ids = Iterator.continually(Map(
    "id" -> counter.getAndIncrement()
  ))

  val echo = feed(ids).exec(
      http("echo")
        .get("/ping/${id}")
        .check(
          status.is(200),
          responseTimeInMillis.lessThanOrEqual(expectedResponseTimeMsec),
          
        )
    )

}
