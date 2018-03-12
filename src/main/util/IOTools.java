package util;

import exception.NotAFolderException;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

public class IOTools {
    public static String exec(String cmd){
        return exec(cmd, 0);
    }

    public static String exec(String cmd, long wait) {
        try {ProcessBuilder builder = new ProcessBuilder( "/bin/bash" );
            Process p=null;
            try {
                p = builder.start();
            }
            catch (IOException e) {
                System.out.println(e);
            }
            BufferedWriter stdin = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
            String[] com = cmd.split(" && ");
            for (String s : com) {
                try {
                    stdin.write(s);
                    stdin.newLine();
                    stdin.flush();
                }
                catch (IOException e) {
                    System.out.println(e);
                }
            }
            try {
                stdin.write("exit");
                stdin.newLine();
                stdin.flush();
            }
            catch (IOException e) {
                System.out.println(e);
            }
            Thread.sleep(wait);
            StringBuilder str = new StringBuilder();
            boolean first = true;
            Scanner s = new Scanner( p.getInputStream() );
            while (s.hasNext())
            {
                if(first)
                    first=!first;
                else
                    str.append(" ");
                str.append(s.next());
            }
            s.close();
            return str.toString();
        } catch (Exception e) {
            System.err.println("Erreur dans l'éxécution du code bash");
            e.printStackTrace();
            return "";
        }
    }

    public static void cleanup() throws NotAFolderException {
        Path sub_folder = Paths.get("Soumission");
        if (!Files.isDirectory(sub_folder)) {
            throw new NotAFolderException("Soumission");
        }
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(sub_folder);
        } catch (IOException e) {
            throw new NotAFolderException("Soumission");
        }
        Iterator<Path> it = stream.iterator();
        while (it.hasNext()) {
            Path tmp = it.next();
            if (Files.isDirectory(tmp) &&
                    !tmp.endsWith(".zip")) {
                FileTools.deleteFolder(tmp.toString());
            }
        }
    }
}
