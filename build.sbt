import Dependencies._

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.example",
      scalaVersion := "2.12.4",
      version := "0.1.0-SNAPSHOT"
    )),
  name := "Hello",
  libraryDependencies += scalaTest              % Test,
  libraryDependencies += "org.rogach"           %% "scallop" % "3.1.1",
  libraryDependencies += "net.jcazevedo"        %% "moultingyaml" % "0.4.0",
  libraryDependencies += "com.github.pathikrit" %% "better-files" % "3.4.0"
)
