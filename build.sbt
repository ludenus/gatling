name := "kaaiot-qa-perf"

version := "1.1"

scalaVersion := "2.12.6"

enablePlugins(GatlingPlugin)

resolvers += DefaultMavenRepository

resolvers += Resolver.sonatypeRepo("public")
resolvers += Resolver.typesafeRepo("releases")
resolvers += Resolver.typesafeIvyRepo("releases")
resolvers += Resolver.sbtPluginRepo("releases")
resolvers += Resolver.jcenterRepo

libraryDependencies += "io.gatling.highcharts" % "gatling-charts-highcharts" % "2.3.1" % "test"

libraryDependencies += "io.gatling" % "gatling-test-framework" % "2.3.1" % "test"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.10"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe" % "config" % "1.3.1"

libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.4"
