#! /bin/sh

mkdir $1
cd $1

password=password
commonname=CA
cert_location=../authority/cert.pem
key_location=../authority/key.pem



# openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -passout pass:$password -passin pass:$password \
#         -subj "CN=CA" 

keytool -import -file $cert_location -alias CA -keystore clienttruststore -storepass $password -noprompt

keytool -genkey -alias key_pair_id -keyalg RSA -validity 365 -keystore clientkeystore -storetype JKS -storepass $password -keypass $password \
                -dname "CN=$commonname"

keytool -certreq -alias key_pair_id -file csr -keystore clientkeystore -storepass $password -noprompt

openssl x509 -req -in csr -CA $cert_location -CAkey $key_location -CAcreateserial -out certificate_chain.pem -passin pass:$password

keytool -importcert -alias CA -file $cert_location -keystore clientkeystore -storepass $password -noprompt

keytool -importcert -alias key_pair_id -file certificate_chain.pem -keystore clientkeystore -storepass $password -noprompt

cp ../../original_client.java client.java
javac client.java