package tut.dush;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;

import com.auth0.jwt.exceptions.JWTCreationException;

import tut.dush.util.FileUtils;
import tut.dush.util.JWTHelper;

/**
 * Encrypt JWT for application
 *
 */
public class App 
{

    private static void printErr() {
        StringBuffer errBuff = new StringBuffer();
        errBuff.append("Mandatory input required for payload file to encrypt");
        errBuff.append('\n');
        errBuff.append("Example: java -jar jwtencrypt.jar -load c:/path/to/file");

        System.err.println(errBuff.toString());
        System.exit(-1);
    }

    public static void main( String[] args )
    {
        if((args == null) ||(args.length == 0)) {
            printErr();
        }

        List<String> arr = Arrays.asList(args);
        int fLoadIdx = arr.indexOf("-load");
        if((fLoadIdx < 0) || (arr.size() <= fLoadIdx+1)) {
            printErr();
        }

        String path = arr.get(fLoadIdx + 1);
        String file="";
        try {
            file = new FileUtils().loadFileAsString(path);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-2);
        }
        //encrypt
        try {
            System.out.println(JWTHelper.getInstance().tokenizePayload(file));
        } catch (JWTCreationException | InvalidKeySpecException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            System.exit(-2);
        }

    }
}
