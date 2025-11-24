ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.11.0"

lazy val root = (project in file("."))
  .settings(
    name := "RatedFilmsPerUser"
  )


libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.0"
