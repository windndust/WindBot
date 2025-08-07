echo "Start test"
jarName="$(mvn help:evaluate -Dexpression=project.build.finalName -DforceStdout -q | sed 's/\[INFO\] \[stdout\] //')".jar
echo $jarName
name=$(mvn help:evaluate -Dexpression=project.artifactId -DforceStdout -q | sed 's/\[INFO\] \[stdout\] //')
echo $name
version=$(mvn help:evaluate -Dexpression=project.version -DforceStdout -q | sed 's/\[INFO\] \[stdout\] //')
echo $version
docker build . --file Dockerfile --tag $name:$version --build-arg JAR_NAME=$jarName --progress=plain --no-cache
docker tag $name:$version $name:latest
docker save $name:$version $name:latest -o ./$name-$version.tar
echo "Done test"