package core;

import data.Project;
import exception.NotAFolderException;
import util.ZipTools;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {

    private List<Project> project_list;

    public List<Project> runAllTests(){
        project_list = new ArrayList<>();

        try {
            findProjectsInSubmissions();
        } catch(NotAFolderException e){
            System.err.println("The submission folder cannot be found");
        }

        return project_list;
    }

    public void findProjectsInSubmissions() throws NotAFolderException {
        Path sub_folder = Paths.get("Soumission");
        if(!Files.isDirectory(sub_folder)) {
            throw new NotAFolderException("Soumission");
        }
        DirectoryStream<Path> stream;
        try {
            stream = Files.newDirectoryStream(sub_folder);
        } catch (IOException e){
            throw new NotAFolderException("Soumission");
        }
        Iterator<Path> it = stream.iterator();
        while(it.hasNext()){
            Path tmp = it.next();
            if(!Files.isDirectory(tmp) &&
                    tmp.toString().endsWith(".zip")){
                ZipTools.UnZip(tmp.toString());
                String filename = tmp.getFileName().toString().replaceAll(".zip","");
                project_list.add(new Project(filename));
                System.out.println("filename");
            }
        }
    }

}
