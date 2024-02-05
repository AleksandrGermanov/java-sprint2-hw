chcp 65001
pushd %~dp0
javac -encoding utf8 -d ./jar -sourcepath ./src ./src/Main.java ./src/StandartEdition/*.java ./src/ShapkinsEdition/*.java
cd jar
jar cfe CountersApp.jar Main *.class ./StandartEdition/*.class ./ShapkinsEdition/*.class
del *.class
del /f /s /q ShapkinsEdition
rmdir ShapkinsEdition
del /f /s /q StandartEdition
rmdir StandartEdition
cd ..
java -jar ./jar/CountersApp.jar