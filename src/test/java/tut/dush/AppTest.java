package tut.dush;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import tut.dush.util.FileUtils;
import tut.dush.util.JWTHelper;
import tut.dush.util.MyException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import com.google.common.base.Strings;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    /**
     * Rigourous Test :-)
     */
    @Test
    @DisplayName("Testing file utils")
    public void testFileUtils()
    {
        FileUtils fUtils = new FileUtils();
        try{
            String out = fUtils.loadFileAsString("/home/dushyant/tut/java/test1.java");
            assertTrue( !Strings.isNullOrEmpty(out) );
            System.out.println(out);
        } catch (IOException | MyException e) {
            fail("Error running test on file utils", e);
        }
        
    }

    @Test
    @DisplayName("Test creating token")
    public void testToken() {
        try{
            String token = JWTHelper.getInstance().tokenizePayload("Test me for tokens");
            String dToken = JWTHelper.getInstance().removeTokenize(token);
            assertEquals("Test me for tokens", dToken, "Token decrypted do not match");
        } catch (Throwable th) {
            fail("Error testing JWT token", th);
        }
    }
}
