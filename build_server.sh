mv src/main/liberty/config/server.template.xml src/main/liberty/config/server.xml

mvn clean install
mvn -B package
mvn liberty:package

mkdir target/liberty/wlp/usr/servers/defaultServer/apps
cp target/asr.trivial.war target/liberty/wlp/usr/servers/defaultServer/apps/

mkdir wlp
mv target/liberty/wlp/usr wlp/
