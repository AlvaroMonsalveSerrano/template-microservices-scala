import Dependencies._
import sbt.Keys.{resolvers, testFrameworks}

ThisBuild / description := "Template of a microservices with http4s, zio and quill"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / organization := "es.ams"
ThisBuild / organizationName := "AMS"
ThisBuild / name := "template-microservices"
ThisBuild / assemblyJarName := "template-microservices.jar"
ThisBuild / developers := List(
  Developer(
    id = "",
    name = "Álvaro Monsalve Serrano",
    email = "",
    url = url("http://alvaromonsalve.com")
  )
)
ThisBuild / assemblyMergeStrategy := {
//  case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case PathList("META-INF", xs @ _*)                 => MergeStrategy.discard
  case "application.conf"                            => MergeStrategy.concat
//  case "unwanted.txt"                                => MergeStrategy.discard
  case x =>
    val oldStrategy = (ThisBuild / assemblyMergeStrategy).value
    oldStrategy(x)
}

lazy val commonSettings = Seq(
  libraryDependencies ++= Seq(scalaTest),
  scalacOptions ++= Seq(
    "-deprecation",
    "-encoding",
    "utf-8",
    "-explaintypes",
    "-feature",
    "-unchecked",
    "-language:postfixOps",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-Xlint",
    "-Xfatal-warnings",
    "-language:reflectiveCalls",
    "-Yrangepos"
  ),
  testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
)

lazy val root = (project in file("."))
  .aggregate(api, services, persistence, model)
  .dependsOn(api)
  .settings(
    name := "template-microservices",
    commonSettings,
    libraryDependencies ++= commonDependencies,
    assembly / assemblyJarName := "template-microserives.jar",
    assembly / mainClass := Some("es.ams.api.app.App")
  )

lazy val commonDependencies = Seq(
  scalaTest,
  scalacheck,
  munit,
  munit_cats_effect_2,
  ciris_ciris
)

lazy val api = (project in file("api"))
  .dependsOn(services)
  .settings(
    name := "api",
    run / mainClass := Some("es.ams.api.app.App"),
    commonSettings,
    libraryDependencies ++=
      apiDependencies ++ commonDependencies
  )

lazy val apiDependencies = Seq(
  http4s_blaze_server,
  http4s_blaze_client,
  http4s_circe,
  http4s_dsl,
  circe_core,
  circe_generic,
  circe_literal,
  circe_parser
)

lazy val services = (project in file("services"))
  .dependsOn(persistence)
  .settings(
    name := "services",
    assembly / assemblyJarName := "services.jar",
    commonSettings,
    libraryDependencies ++= serviceDependencies ++ commonDependencies
  )

lazy val serviceDependencies = Seq(
  zio_core,
  zio_interop_cats,
  zio_streams,
  zio_test,
  zio_test_sbt,
  zio_test_magnolia,
  testcontainers_scalatest,
  testcontainers_munit,
  testcontainers_postgresql,
  postgres
)

lazy val persistence = (project in file("persistence"))
  .dependsOn(model)
  .settings(
    name := "persistence",
    assembly / assemblyJarName := "persistence.jar",
    commonSettings,
    unmanagedClasspath in Compile += baseDirectory.value / "src" / "main" / "resources",
    // include the macro classes and resources in the main jar
//    Compile / packageBin / mappings ++= (macroMQuill / Compile / packageBin / mappings).value,
    // include the macro sources in the main source jar
//    Compile / packageSrc / mappings ++= (macroMQuill / Compile / packageSrc / mappings).value,
    libraryDependencies ++= persistenceDependencies ++ commonDependencies
  )

lazy val persistenceDependencies = Seq(
  quill,
  quill_sql,
  quillJdbc,
  quillH2,
  quillPostgres,
  testcontainers_scalatest,
  testcontainers_munit,
  testcontainers_postgresql,
  postgres
)

lazy val model = (project in file("model"))
  .settings(
    name := "model",
    assembly / assemblyJarName := "model.jar",
    commonSettings,
    libraryDependencies ++=
      serviceDependencies ++ Seq(
        scalaTest
      )
  )

// Add libraries to process model.
lazy val modelDependencies = Seq(
)
