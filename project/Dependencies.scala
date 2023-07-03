import sbt._

object Dependencies {

  lazy val shapelessVersion    = "2.3.10"
  lazy val catsVersion         = "2.1.1"
  lazy val catsEffectVersion   = "2.1.1"
  lazy val fs2Version          = "3.7.0"
  lazy val scalaTestVersion    = "3.2.16"
  lazy val scalaCheckVersion   = "1.17.0"
  lazy val munitVersion        = "0.7.29"
  lazy val refinedVersion      = "0.11.0"
  lazy val singletonOpsVersion = "0.4.3"

  lazy val shapeless    = "com.chuusai"    %% "shapeless"     % shapelessVersion
  lazy val catsEffect   = "org.typelevel"  %% "cats-effect"   % catsEffectVersion
  lazy val fs2Core      = "co.fs2"         %% "fs2-core"      % fs2Version
  lazy val fs2Io        = "co.fs2"         %% "fs2-io"        % fs2Version
  lazy val scalaTest    = "org.scalatest"  %% "scalatest"     % scalaTestVersion
  lazy val scalaTestApp = "org.scalatest"  %% "scalatest-app" % scalaTestVersion
  lazy val scalaCheck   = "org.scalacheck" %% "scalacheck"    % scalaCheckVersion
  lazy val munit        = "org.scalameta"  %% "munit"         % munitVersion
  lazy val refined      = "eu.timepit"     %% "refined"       % refinedVersion
  lazy val singletonOps = "eu.timepit"     %% "singleton-ops" % singletonOpsVersion

  // compilerPlugins
  lazy val kindProjectorVersion    = "0.13.2"
  lazy val betterMonadicForVersion = "0.3.1"

  // https://github.com/typelevel/kind-projector
  lazy val kindProjectorPlugin = compilerPlugin(
    compilerPlugin("org.typelevel" % "kind-projector" % kindProjectorVersion cross CrossVersion.full)
  )
  // https://github.com/oleg-py/better-monadic-for
  lazy val betterMonadicForPlugin = compilerPlugin(
    compilerPlugin("com.olegpy" %% "better-monadic-for" % betterMonadicForVersion)
  )
}
