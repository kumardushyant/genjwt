package tut.dush.util;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTHelper {

    private FileUtils fileUtils = new FileUtils();
    private RSAPublicKey publicKey;
    private RSAPrivateKey privateKey;

    public static JWTHelper instance = null;

    public static JWTHelper getInstance() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        if(instance == null) {
            instance = new JWTHelper();
        }

        return instance;
    }

    private JWTHelper() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        loadKeys();
    }

    private void loadKeys() throws InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        String privStr = fileUtils.loadFileAsString("/home/dushyant/certs/priv.crt");
        String pubStr = fileUtils.loadFileAsString("/home/dushyant/certs/pub1.crt");
        
        privStr = privStr.replace("-----BEGIN PRIVATE KEY-----","").replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\n", "").replaceAll("\\r", "");
        pubStr = pubStr.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\n", "").replaceAll("\\r", "");
        
        KeyFactory kf = KeyFactory.getInstance("RSA");

        byte[] bytes = Base64.getDecoder().decode(privStr);

        PKCS8EncodedKeySpec pkeySpec = new PKCS8EncodedKeySpec(bytes);
        privateKey = (RSAPrivateKey) kf.generatePrivate(pkeySpec);
        
        bytes = Base64.getDecoder().decode(pubStr);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        publicKey = (RSAPublicKey) kf.generatePublic(keySpec);
    }

    public String tokenizePayload(String payload) throws JWTCreationException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {

        Algorithm algo = Algorithm.RSA256(publicKey, privateKey);
        return JWT.create().withIssuer(payload).sign(algo);
    }

    public String removeTokenize(String inp) {
        DecodedJWT decode = JWT.decode(inp);
        String payload = new String(Base64.getDecoder().decode(decode.getPayload()));
        return payload.substring(payload.indexOf(":")+2,payload.length()-2);
    }
    
}
