package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipTools {

    public static void UnZip(String inputFile, String outputDirectory) {
        //todo test if file exists

        byte[] buffer = new byte[1024];

        try {

            File folder = new File(outputDirectory);
            if (!folder.exists()) {
                folder.mkdir();
            }

            ZipInputStream zis =
                    new ZipInputStream(new FileInputStream(inputFile));

            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();
                File newFile = new File(outputDirectory + File.separator + fileName);

                if(!new File(newFile.getParent()).mkdirs())
                    System.err.println("There was an error");

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
