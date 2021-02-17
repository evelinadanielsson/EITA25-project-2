#! /bin/sh

mkdir $1
cd $1

password=olivia123
country=SE
state=Skane
locality=Lund
organization=None
organizationalunit=None
commonname=Olivia
email=fake@email.com



openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -passout pass:$password -passin pass:$password \
        -subj "/C=$country/ST=$state/L=$locality/O=$organization/OU=$organizationalunit/CN=$commonname/emailAddress=$email" 

keytool -import -file cert.pem -alias CA -keystore clienttruststore -storepass $password -noprompt

keytool -genkey -alias key_pair_id  -keyalg RSA -validity 365 -keystore clientkeystore -storetype JKS -storepass $password -keypass $password \
        -dname "CN=$commonname, OU=$organizationalunit, O=$organization, C=$country, ST=$state, L=$locality"

keytool -certreq -alias key_pair_id  -file csr -keystore clientkeystore -storepass $password -noprompt

openssl x509 -req -in csr -CA cert.pem -CAkey key.pem -CAcreateserial -out certificate_chain.pem -passin pass:$password

keytool -importcert -alias CA -file cert.pem -keystore clientkeystore -storepass $password -noprompt

keytool -importcert -alias key_pair_id -file certificate_chain.pem -keystore clientkeystore -storepass $password -noprompt