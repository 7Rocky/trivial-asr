c=`cf service-key trivial-asr-app-id Credentials | grep clientId | awk '{ print $2 }' | tr ',' ' ' | xargs`
s=`cf service-key trivial-asr-app-id Credentials | grep secret | awk '{ print $2 }' | tr ',' ' ' | xargs`
d=`cf service-key trivial-asr-app-id Credentials | grep discoveryEndpoint | awk '{ print $2 }' | tr ',' ' ' | xargs`

sed -i "s/AppId-clientId/$c/g" wlp/usr/servers/defaultServer/server.xml
sed -i "s/AppId-clientSecret/$s/g" wlp/usr/servers/defaultServer/server.xml
sed -i "s%AppId-discoveryEndpointUrl%$d%g" wlp/usr/servers/defaultServer/server.xml
sed -i "s%http://localhost:9080%https://trivial-asr.eu-gb.mybluemix.net%g" wlp/usr/servers/defaultServer/server.xml
sed -i 's/httpsRequired="false"/httpsRequired="true"/g' wlp/usr/servers/defaultServer/server.xml

zip -r asr.trivial.zip wlp
