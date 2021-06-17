import Dependencies._
import sbt.Keys.{resolvers, testFrameworks}

ThisBuild / description := "Template of a microservices with http4s, zio and quill"
ThisBuild / scalaVersion := "2.13.6"
ThisBuild / version := "1.0.0-SNAPSHOT"
ThisBuild / organization := "es.ams"
ThisBuild / organizationName := "AMS"
ThisBuild / developers := List(
  Developer(
    id = "",
    name = "Álvaro Monsalve Serrano",
    email = "",
    url = url("http://alvaromonsalve.com")
  )
)

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
  .aggregate(api)
  .aggregate(services)
  .aggregate(persistence)
  .aggregate(model)
  //  .settings(BuildInfoSettings.value)
  .settings(
    name := "template-microservices",
    commonSettings,
    libraryDependencies ++= commonDependencies
//    resolvers ++= Seq(
//      "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
//      Resolver.sonatypeRepo("releases"),
//      Resolver.sonatypeRepo("snapshots"),
//      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
//    )
  )

lazy val commonDependencies = Seq(
  scalaTest,
  scalacheck,
  munit,
  munit_cats_effect_2
)

lazy val api = (project in file("api"))
  .dependsOn(services)
  .settings(
    name := "api",
    commonSettings,
    libraryDependencies ++=
      apiDependencies ++ Seq(
        scalaTest,
        munit,
        munit_cats_effect_2
      )
  )

lazy val apiDependencies = Seq(
  http4s_blaze_server,
  http4s_blaze_client,
  http4s_circe,
  http4s_dsl,
  circe_generic,
  circe_literal
)

lazy val services = (project in file("services"))
  .settings(
    name := "services",
    commonSettings,
    libraryDependencies ++=
      serviceDependencies ++ Seq(
        scalaTest
      )
  )

lazy val serviceDependencies = Seq(
  zio_core,
  zio_streams,
  zio_test,
  zio_test_sbt,
  zio_test_magnolia
)

lazy val persistence = (project in file("persistence"))
  .settings(
    name := "persistence",
    commonSettings,
    unmanagedClasspath in Compile += baseDirectory.value / "src" / "main" / "resources",
    // include the macro classes and resources in the main jar
//    Compile / packageBin / mappings ++= (macroMQuill / Compile / packageBin / mappings).value,
    // include the macro sources in the main source jar
//    Compile / packageSrc / mappings ++= (macroMQuill / Compile / packageSrc / mappings).value,
    libraryDependencies ++=
      persistenceDependencies ++ Seq(
        scalaTest,
        munit,
        munit_cats_effect_2
      )
  )

lazy val persistenceDependencies = Seq(
  quill,
  quill_sql,
  quillJdbc,
  quillH2,
  quillPostgres,
  postgres
)

lazy val model = (project in file("model"))
  .settings(
    name := "model",
    commonSettings,
    libraryDependencies ++=
      serviceDependencies ++ Seq(
        scalaTest
      )
  )

// Add libraries to process model.
lazy val modelDependencies = Seq(
)

lazy val assemblySettings = ThisBuild / assemblyMergeStrategy := {
  case "module-info.class" => MergeStrategy.last
  case "application.conf"  => MergeStrategy.concat
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}
