export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
export PATH=$JAVA_HOME/bin:$PATH
./gradlew clean teavmc

chmod +x ./GetRepositorySignature.sh
./GetRepositorySignature.sh

chmod +x ./CompileEPK.sh
./CompileEPK.sh

chmod +x ./ZipStableDownload.sh
./ZipStableDownload.sh