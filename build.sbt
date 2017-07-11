name := "influx-scala-client"

version := "1.0"

organization := "com.fsanaulla"

scalaVersion := "2.12.0"

scalacOptions ++= Seq(
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps"
)

lazy val Versions = new {
  val akka = "10.0.8"
  val whisk = "0.9.0"
}

libraryDependencies ++= Seq(
  "com.typesafe.akka"   %    "akka-http_2.12"               %   Versions.akka,
  "com.whisk"           %%   "docker-testkit-scalatest"     %   Versions.whisk    % "test",
  "com.whisk"           %%   "docker-testkit-impl-spotify"  %   Versions.whisk    % "test",
  "org.scalatest"       %    "scalatest_2.12"               %   "3.0.3"           % "test"
)
