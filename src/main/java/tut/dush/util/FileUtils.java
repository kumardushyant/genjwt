package tut.dush.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.base.Strings;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

public class FileUtils {

    private File checkInput(String path) {
        if (Strings.isNullOrEmpty(path))
            throw new MyException("Input for load file is empty.");

        File file = new File(path);

        if (!Files.isFile().apply(file))
            throw new MyException("Given file at path " + path + " is not a file.");
        
        return file;
    }
    
    public String loadFileAsString(String path) throws IOException {
        
        File file = checkInput(path);
        
        CharSource outchars = Files.asCharSource(file, Charset.forName("UTF-8"));

        return outchars.read();
    }

    public byte[] loadFileAsBytes(String path) throws IOException {
        File file = checkInput(path);

        return Files.asByteSource(file).read();
    }

    public void saveFile(String data) throws IOException {
        FileOutputStream outS = new FileOutputStream(new File("temp.txt"), true);
        outS.write(data.getBytes());
        outS.flush();
        outS.close();
    }
}

