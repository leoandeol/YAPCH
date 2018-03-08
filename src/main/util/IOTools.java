package util;

import java.util.Scanner;

public class IOTools {
    public static String exec(String cmd) throws java.io.IOException {
        Scanner s = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
