enablePlugins(org.nlogo.build.NetLogoExtension)

netLogoExtName      := "pozyx"

netLogoClassManager := "PozyxExtension"

scalaVersion           := "2.11.7"

scalaSource in Compile := baseDirectory.value / "src"

scalacOptions          ++= Seq("-deprecation", "-unchecked", "-Xfatal-warnings", "-encoding", "us-ascii")


javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")


libraryDependencies ++= Seq( 
	"org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.1.0",
	"com.google.code.gson" % "gson" % "2.6.2"
)

initialize := {
  val _ = initialize.value
  val javaVersion = sys.props("java.specification.version")
  if (javaVersion != "1.8")
    sys.error("Java 1.8 is required for this project. Found " + javaVersion + " instead")
}


netLogoVersion := "6.1.1"

fork in run := true
