import sbt._

/**
  * Created by
  * Author: fayaz.sanaulla@gmail.com
  * Date: 28.08.17
  */
object Dependencies {

  final val scalaMeta = "org.scalameta" %% "scalameta" % Versions.scalaMeta
  final val paradise = "org.scalameta" % "paradise" % "3.0.0-M8" cross CrossVersion.full
  final val sprayJson = "com.typesafe.akka" %% "akka-http-spray-json" % Versions.akkaHttp
  final val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest % "test"

  final val asyncHttp = "com.softwaremill.sttp" %% "async-http-client-backend-future" % "1.1.4"

  final val akkaHttp = "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp

  final val projectResolvers = Seq(
    Resolver.sonatypeRepo("releases"),
    Resolver.bintrayRepo("scalameta", "maven")
  )

  final val rootDependencies = Seq(
    akkaHttp,
    sprayJson,
    scalaMeta,
    scalaTest,
    compilerPlugin(paradise)
  )

  final val coreDep = Seq(sprayJson, scalaTest, scalaMeta, compilerPlugin(paradise))
  final val akkaHttpDep = Seq(akkaHttp)
  final val asyncHttpDep = Seq(asyncHttp)
}
