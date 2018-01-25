package util;

import java.io.File;

public class FileTools {

    public static void deleteFolder(String path_to_folder) {
        File folder = new File(path_to_folder);
        File[] files = folder.listFiles();
        if(files!=null) {
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f.toPath().toString());
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

}
