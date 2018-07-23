package load.base

import io.gatling.core.Predef._
import io.gatling.http.Predef.{http, jsonPath, responseTimeInMillis, status, xpath}

import scala.concurrent.duration._

object SparkStats extends Object with Log with Config {

  log.info("{}", config)

  val protocol = http
    .baseURL("")
    .userAgentHeader("Gatling Performance Test")

  val getSparkContextUrl = doIf(session => !session.contains("sparkContextUrl")) {
    exec(
      http("SparkContextUrl")
        .get(s"http://${config.spark.host}:${config.spark.port}")
        .check(
          status.is(200),
          responseTimeInMillis.lessThanOrEqual(10000),
          xpath("//a[text()='DataProcessor']/@href").saveAs("sparkContextUrl")
        )
    ).exec { session =>
      log.info("sparkContextUrl: {}", session("sparkContextUrl").as[String])
      session
    }
  }

  val getApplicationId = doIf(session => !session.contains("applicationId")) {
    exec(
      http("ApplicationId")
        .get("${sparkContextUrl}/api/v1/applications")
        .check(
          status.is(200),
          responseTimeInMillis.lessThanOrEqual(10000),
          jsonPath("$[0].name").exists,
          jsonPath("$[?(@.name==\"DataProcessor\")].id").saveAs("applicationId")
        )
    ).exec { session =>
      log.info("applicationId: {}", session("applicationId").as[String])
      session
    }
  }

  val checkMaxProcessingTime = pause(5 seconds)
    .exec(
      http("MaxProcessingTime")
        .get("${sparkContextUrl}/api/v1/applications/${applicationId}/streaming/batches")
        .check(
          status.is(200),
          responseTimeInMillis.lessThanOrEqual(10000),
          jsonPath("$[0].batchId").exists,
          jsonPath("$..processingTime").findAll.transform { times =>
            val max = times.max.toInt
            log.info("maxProcessingTime: {}", max)
            max
          }.lessThanOrEqual(1000)
        )
    )

}
