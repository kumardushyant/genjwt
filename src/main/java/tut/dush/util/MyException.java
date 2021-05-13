package tut.dush.util;

public class MyException extends RuntimeException {
    
    public MyException(String inp) {
        super(inp);
    }

    public MyException(String inp, Throwable cause) {
        super(inp, cause);
    }
}
