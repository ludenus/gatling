package load.base

import com.typesafe.config.{ConfigBeanFactory, ConfigFactory}

import scala.beans.BeanProperty

trait Config {
  val config = ConfigBeanFactory.create(ConfigFactory.load(), classOf[TestConfig]);
}

class TestConfig {
  @BeanProperty var loadProfile: LoadProfile = null
  @BeanProperty var spark: Spark = null

  override def toString = s"TestConfig(loadProfile=$loadProfile, spark=$spark)"
}

class LoadProfile {
  @BeanProperty var waitSeconds = 1
  @BeanProperty var rampUsers = 10
  @BeanProperty var rampSeconds = 10
  @BeanProperty var maxDurationSeconds = 20
  @BeanProperty var expectedResponseTimeMsec = 4000

  override def toString = s"LoadProfile(waitSeconds=$waitSeconds, rampUsers=$rampUsers, rampSeconds=$rampSeconds, maxDurationSeconds=$maxDurationSeconds, expectedResponseTimeMsec=$expectedResponseTimeMsec)"
}

class Spark {
  @BeanProperty var host = "localhost"
  @BeanProperty var port = 8080


  override def toString = s"Spark(host=$host, port=$port)"
}
