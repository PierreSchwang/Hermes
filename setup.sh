#!/bin/sh

mkdir -p .temp
cd .temp || exit

echo "Downloading NMS Files..."

curl https://cdn.getbukkit.org/spigot/spigot-1.8.8-R0.1-SNAPSHOT-latest.jar -O spigot-1.8.8-R0.1-SNAPSHOT-latest.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.9.4-R0.1-SNAPSHOT-latest.jar -O spigot-1.9.4-R0.1-SNAPSHOT-latest.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.10-R0.1-SNAPSHOT-latest.jar -O spigot-1.10-R0.1-SNAPSHOT-latest.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.11.jar -O spigot-1.11.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.12.jar -O spigot-1.12.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.13.2.jar -O spigot-1.13.2.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.14.4.jar -O spigot-1.14.4.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.15.2.jar -O spigot-1.15.2.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.16.1.jar -O spigot-1.16.1.jar
curl https://cdn.getbukkit.org/spigot/spigot-1.16.3.jar -O spigot-1.16.3.jar

echo "Installing nms libraries"

mvn install:install-file -D"file=spigot-1.8.8-R0.1-SNAPSHOT-latest.jar" -D"version=1.8.8-R0.1-SNAPSHOT" \
  -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.9.4-R0.1-SNAPSHOT-latest.jar" -D"version=1.9.4-R0.1-SNAPSHOT" \
  -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.10-R0.1-SNAPSHOT-latest.jar" -D"version=1.10-R0.1-SNAPSHOT" \
  -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.11.jar" -D"version=1.11-R0.1-SNAPSHOT" \
  -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.12.jar" -D"version=1.12-R0.1-SNAPSHOT" \
   -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.13.2.jar" -D"version=1.13.2-R0.1-SNAPSHOT" \
   -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.14.4.jar" -D"version=1.14.4-R0.1-SNAPSHOT" \
   -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.15.2.jar" -D"version=1.15.2-R0.1-SNAPSHOT" \
   -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.16.1.jar" -D"version=1.16.1-R0.1-SNAPSHOT" \
   -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"
mvn install:install-file -D"file=spigot-1.16.3.jar" -D"version=1.16.3-R0.1-SNAPSHOT" \
   -D"groupId=org.spigotmc" -D"artifactId=spigot" -D"packaging=jar"