val scala3Version = "3.0.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
    version := "0.1.0",

    scalaVersion := scala3Version,

    libraryDependencies += ("org.creativescala" %% "doodle" % "0.9.23").cross(CrossVersion.for3Use2_13),
    libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1",
    libraryDependencies += ("org.scalaz" %% "scalaz-core" % "7.3.3").cross(CrossVersion.for3Use2_13),
  )
