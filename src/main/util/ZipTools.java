package util;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipTools {

    // function to simplify if we dont want to give an output dir
    public static void UnZip(String inputFile){
        String folder = inputFile.replaceAll(".zip", "");
        try {
            UnZip(inputFile, folder);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void UnZip(String inputFile, String outputDirectory) throws IOException {
        int BUFFER = 2048;
        File file = new File(inputFile);

        ZipFile zip = new ZipFile(file);
        String newPath = outputDirectory;

        File f = new File(newPath);
        f.mkdir();

        Enumeration zipFileEntries = zip.entries();

        while (zipFileEntries.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry)zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File(newPath, currentEntry);

            File parent = destFile.getParentFile();

            parent.mkdirs();

            if (!entry.isDirectory()){
                BufferedInputStream is = new BufferedInputStream(zip
                        .getInputStream(entry));
                int currentByte;
                byte data[] = new byte[BUFFER];
                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos,
                        BUFFER);
                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }
                dest.flush();
                dest.close();
                is.close();
            }
        }
    }

}
