import sbt._

object Dependencies {

  lazy val http4s_blaze_server = "org.http4s" %% "http4s-blaze-server" % Versions.http4s
  lazy val http4s_blaze_client = "org.http4s" %% "http4s-blaze-client" % Versions.http4s
  lazy val http4s_dsl          = "org.http4s" %% "http4s-dsl"          % Versions.http4s
  lazy val http4s_circe        = "org.http4s" %% "http4s-circe"        % Versions.http4s
  lazy val circe_generic       = "io.circe"   %% "circe-generic"       % Versions.circe
  lazy val circe_literal       = "io.circe"   %% "circe-literal"       % Versions.circe

  lazy val zio_core          = "dev.zio" %% "zio"               % Versions.zio
  lazy val zio_streams       = "dev.zio" %% "zio-streams"       % Versions.zio
  lazy val zio_test          = "dev.zio" %% "zio-test"          % Versions.zio % "test"
  lazy val zio_test_sbt      = "dev.zio" %% "zio-test-sbt"      % Versions.zio % "test"
  lazy val zio_test_magnolia = "dev.zio" %% "zio-test-magnolia" % Versions.zio % "test" // optional

  lazy val quill         = "io.getquill"   %% "quill-sql-portable"   % Versions.quill
  lazy val quill_sql     = "io.getquill"   %% "quill-sql"            % Versions.quill
  lazy val quillJdbc     = "io.getquill"   %% "quill-jdbc"           % Versions.quill
  lazy val quillPostgres = "io.getquill"   %% "quill-async-postgres" % Versions.quill
  lazy val quillH2       = "com.h2database" % "h2"                   % Versions.quill_h2

  lazy val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalatest % Test

  lazy val scalacheck = "org.scalacheck" %% "scalacheck" % Versions.scalacheck % Test

  lazy val munit               = "org.scalameta" %% "munit"               % Versions.munit                     % Test
  lazy val munit_cats_effect_2 = "org.typelevel" %% "munit-cats-effect-2" % Versions.munit_cats_effect_version % Test

  lazy val cats_core        = "org.typelevel" %% "cats-core"        % Versions.cats_core
  lazy val cats_effect      = "org.typelevel" %% "cats-effect"      % Versions.cats_effect
  lazy val cats_effect_laws = "org.typelevel" %% "cats-effect-laws" % Versions.cats_effect % "test"
  lazy val cats_free        = "org.typelevel" %% "cats-free"        % Versions.cats_free

  lazy val ciris_ciris        = "is.cir"     %% "ciris"            % Versions.ciris
  lazy val ciris_circe        = "is.cir"     %% "ciris-circe"      % Versions.ciris
  lazy val ciris_enumeratum   = "is.cir"     %% "ciris-enumeratum" % Versions.ciris
  lazy val ciris_refined      = "is.cir"     %% "ciris-refined"    % Versions.ciris
  lazy val ciris_squants      = "is.cir"     %% "ciris-squants"    % Versions.ciris
  lazy val ciris_refined_cats = "eu.timepit" %% "refined-cats"     % Versions.refined_cats

  lazy val postgres = "org.postgresql" % "postgresql" % "42.2.8"

  lazy val mysql_connector_java = "mysql" % "mysql-connector-java" % Versions.mysql_connector

}
