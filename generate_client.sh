#! /bin/sh

mkdir $1
cd $1

password=password
country=SE
state=Skane
locality=Lund
organization=None
organizationalunit=None
commonname=CA
email=fake@email.com



openssl req -x509 -newkey rsa:4096 -keyout key.pem -out cert.pem -days 365 -passout pass:$password -passin pass:$password \
        -subj "/C=$country/ST=$state/L=$locality/O=$organization/OU=$organizationalunit/CN=$commonname/emailAddress=$email" 

# keytool -import -file cert.pem -alias CA_$commonname -keystore clienttruststore -storepass $password -noprompt

# keytool -genkey -alias key_pair_id_$commonname -keyalg RSA -validity 365 -keystore clientkeystore -storetype JKS -storepass $password -keypass $password \
#         -dname "CN=$commonname, OU=$organizationalunit, O=$organization, C=$country, ST=$state, L=$locality"

# keytool -certreq -alias key_pair_id_$commonname -file csr -keystore clientkeystore -storepass $password -noprompt

# openssl x509 -req -in csr -CA cert.pem -CAkey key.pem -CAcreateserial -out certificate_chain.pem -passin pass:$password

# keytool -importcert -alias CA_$commonname -file cert.pem -keystore clientkeystore -storepass $password -noprompt

# keytool -importcert -alias key_pair_id_$commonname -file certificate_chain.pem -keystore clientkeystore -storepass $password -noprompt

# cp ../../original_client.java client.java
# javac client.java