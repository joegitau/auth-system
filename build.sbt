lazy val AkkaVersion = "2.8.0"
lazy val SlickVersion = "3.4.1"
lazy val CirceVersion = "0.14.5"
lazy val SlickPgVersion = "0.21.1"
lazy val RefinedVersion = "0.10.3"
lazy val AkkaHttpVersion = "10.5.0"
lazy val PostgresqlVersion = "42.5.4"

lazy val root = (project in file("."))
  .settings(
    name         := "auth-system",
    version      := "1.0.0",
    scalaVersion := "2.13.10",
    libraryDependencies ++=Seq(
      // actors
      "com.typesafe.akka"        %% "akka-actor-typed"           % AkkaVersion,
      // akka
      "com.typesafe.akka"        %% "akka-http"                  % AkkaHttpVersion,
      "com.typesafe.akka"        %% "akka-stream"                % AkkaVersion,
      "com.typesafe.akka"        %% "akka-serialization-jackson" % AkkaVersion,
      "com.typesafe.slick"       %% "slick"                      % SlickVersion,
      // db
      "org.postgresql"           % "postgresql"                  % PostgresqlVersion,
      "com.typesafe.slick"       %% "slick-hikaricp"             % "3.4.1",
      "com.github.tminglei"      %% "slick-pg"                   % SlickPgVersion,
      // marshalling
      "com.github.tminglei"      %% "slick-pg_spray-json"        % SlickPgVersion,
      "com.github.tminglei"      %% "slick-pg_circe-json"        % SlickPgVersion,
      "com.github.tminglei"      %% "slick-pg_play-json"         % SlickPgVersion,
      "com.github.tminglei"      %% "slick-pg_play-json"         % SlickPgVersion,
      "com.typesafe.akka"        %% "akka-http-spray-json"       % AkkaHttpVersion,
      "io.circe"                 %% "circe-core"                 % CirceVersion,
      "io.circe"                 %% "circe-generic"              % CirceVersion,
      "io.circe"                 %% "circe-parser"               % CirceVersion,
      "de.heikoseeberger"        %% "akka-http-circe"            % "1.39.2",
      // jwt
      "com.github.jwt-scala"     %% "jwt-circe"                  % "9.2.0",
      // di
      "com.softwaremill.macwire" %% "macros"                     % "2.5.8" % "provided",
      "com.softwaremill.macwire" %% "util"                       % "2.5.8",
      // quill
      "io.getquill"              %% "quill-sql"                  % "4.6.0",
      // refined
      "eu.timepit"               %% "refined"                    % RefinedVersion,
      "at.favre.lib"             % "bcrypt"                      % "0.10.2",
      // logging
      "com.typesafe.akka"        %% "akka-slf4j"                 % AkkaVersion,
      "ch.qos.logback"           % "logback-classic"             % "1.2.11",
      // tests
      "com.typesafe.akka"        %% "akka-actor-testkit-typed"   % AkkaVersion % Test,
      "com.typesafe.akka"        %% "akka-stream-testkit"        % AkkaVersion % Test,
      "org.scalatest"            %% "scalatest"                  % "3.2.15"    % Test,
    ),
    scalacOptions ++= Seq(
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xfatal-warnings",
      "-language:higherKinds",
      "-language:implicitConversions"
    )
  )
