package core;

import data.Project;
import exception.NotAFolderException;
import util.IOTools;
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

    public List<Project> getProject_list() {
        return project_list;
    }

    private List<Project> project_list;

    public Test(){
        project_list = new ArrayList<>();
    }

    public List<Project> runAllTests(){

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
            }
        }
    }

    public void checkProjects(){
        for(Project p : project_list){
            //revoir l'architecture des projects
        }
    }

    public void compileProjects(){
        for(Project p : project_list){
            try {
                Runtime.getRuntime().exec("cd "+p.getName()+" && find -name *.java | xargs javac && cd ..").waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void compileJavadoc(){
        for(Project p : project_list){
            try {
                Runtime.getRuntime().exec("cd "+p.getName()+" && find -name *.java | xargs java -d && cd ..").waitFor();
                //test existance du dossier docs ? et rempli ?
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void compileUnitTest(){
        for(Project p : project_list) {
            try {
                String s = IOTools.exec("cd "+p.getName()+" && find -name Test*.java && cd ..");
                if(s.equals("")){
                    p.setUnitTested(Project.State.BROKEN);
                    continue;
                }
                Runtime.getRuntime().exec("cd " + p.getName() + " && find -name Test*.java | xargs javac -cp .:junit-4.12.jar:hamcrest-core-1.3.jar && cd ..").waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeUnitTest(){
        for(Project p : project_list){
            try {
                // foreach file we remove .class from the test and execute it
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
