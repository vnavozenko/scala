name := "processstTodoList"

version := "1.0"

lazy val `processsttodolist` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc ,
  "com.typesafe.play" %% "anorm" % "2.5.1",
  "org.postgresql" % "postgresql" % "9.4.1212",
  cache,
  ws,
  evolutions,
  specs2 % Test
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  