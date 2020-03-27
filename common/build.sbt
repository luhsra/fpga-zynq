import sbt.complete._
import sbt.complete.DefaultParsers._

lazy val commonSettings = Seq(
  organization := "edu.berkeley.cs",
  version      := "1.2",
  scalaVersion := "2.12.4",
  crossScalaVersions := Seq("2.12.4"),
  parallelExecution in Global := false,
  traceLevel   := 15,
  scalacOptions ++= Seq("-deprecation","-unchecked","-Xsource:2.11"),
  libraryDependencies ++= Seq("org.scala-lang" % "scala-reflect" % scalaVersion.value),
  libraryDependencies ++= Seq("org.json4s" %% "json4s-jackson" % "3.5.3"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)

lazy val zynq = (project in file(".")).settings(commonSettings)

