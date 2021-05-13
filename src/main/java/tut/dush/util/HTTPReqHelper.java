package tut.dush.util;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.apache.v2.ApacheHttpTransport;
import com.google.common.io.Resources;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContexts;

public class HTTPReqHelper {

    private HttpRequestFactory httpFactory = null;

    private HttpClient getHttpClient() throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException, KeyManagementException, UnrecoverableKeyException {
        KeyStore ks = KeyStore.getInstance("pkcs12");
        ks.load(Resources.getResource("keys.p12").openStream(), "aramank@123".toCharArray());

        SSLContext sslCtx = SSLContexts.custom().loadKeyMaterial(ks, "aramank@123".toCharArray()).loadKeyMaterial(ks, "aramank@123".toCharArray()).build();

        return HttpClientBuilder.create().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE).setSSLContext(sslCtx).build();
    }

    public HTTPReqHelper() {
        try {
            httpFactory = new ApacheHttpTransport(getHttpClient()).createRequestFactory();
        } catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | CertificateException
                | KeyStoreException | IOException e) {
            throw new MyException("Error loading keystore", e);
        }
    }

    public String get() {
        return null;
    }
    
}
