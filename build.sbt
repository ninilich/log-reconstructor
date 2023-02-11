import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "log-reconstructor",
    assembly / mainClass := Some("Main"),
    assembly / assemblyJarName := s"log-reconstructor-${version.value}.jar",
    libraryDependencies ++= Seq(
      "org.json4s" %% "json4s-native" % "4.1.0-M2",
      "org.scalatest" %% "scalatest-funsuite" % "3.2.15" % "test"
    ),
    assembly / assemblyOutputPath := file(s"${(assembly / assemblyJarName).value}")

  )

