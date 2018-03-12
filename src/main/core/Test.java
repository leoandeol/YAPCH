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
            this.findProjectsInSubmissions();
            this.checkProjects();
            this.compileProjects();
            this.compileJavadoc();
            this.compileTest();
            this.compileUnitTest();
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
            String s = IOTools.exec("cd Soumission && cd "+p.getName()+" && cd "+p.getName() +" && ls | grep .java");
            if(s.split(" ").length>1){
                p.setCorrect(true);
            } else {
                p.setCorrect(false);
            }
        }
    }

    public void compileProjects(){
        for(Project p : project_list){
            if(p.isCorrect()) {
                IOTools.exec("cd Soumission && cd "+p.getName()+" && cd "+p.getName() +" && find . -name '*.java' | xargs javac");
                boolean test = (IOTools.exec("cd Soumission/AsterixObelix/AsterixObelix && ls | grep .java").split(" ").length ==
                        IOTools.exec("cd Soumission/AsterixObelix/AsterixObelix && ls | grep .class").split(" ").length);
                p.setCompiles(test);
            } else {
                p.setCompiles(false);
            }
        }
    }

    public void compileJavadoc(){
        for(Project p : project_list){
            if(p.isCorrect()){
                String s = IOTools.exec("cd Soumission && cd "+p.getName()+" && cd "+p.getName() +" && egrep -lir --include=*.java \"(@param|@return)\"");
                if(s.contains(".java")) {
                    IOTools.exec("cd Soumission && cd "+p.getName()+" && cd "+p.getName() +" && javadoc $(find . -name \"*.java\") -d docs/\n");
                    p.setJavadocTested(Project.State.WORKING);
                }
                else {
                    p.setJavadocTested(Project.State.NOTAVAILABLE);
                }
            } else {
                p.setJavadocTested(Project.State.BROKEN);
            }
        }
    }

    public void compileUnitTest(){
        String s = IOTools.exec("ls testUnitaires | grep .java");
        String[] unittests = s.split(" ");
        for(Project p : project_list) {
            if(p.isCorrect()){
                if(s.equals("")){
                    p.setUnitTested(Project.State.BROKEN);
                    continue;
                }
                boolean test = true;
                for(String str : unittests){
                    String cls = str.replaceAll(".java","");
                    String res = IOTools.exec("cd Soumission && cd "+p.getName()+" && cd "+p.getName() +" && cp ../../../testUnitaires/"+cls+".java . && "+
                            "javac -cp .:../../../junit-4.12.jar:../../../hamcrest-core-1.3.jar "+str+" && "+
                            "java -cp .:../../../junit-4.12.jar:../../../hamcrest-core-1.3.jar org.junit.runner.JUnitCore "+cls);
                    if(res.contains("FAILURES!!!")){
                        test=false;
                        break;
                    }
                }
                p.setUnitTested(test ? Project.State.WORKING : Project.State.BROKEN);
            } else{
                p.setUnitTested(Project.State.BROKEN);
            }
        }
    }

    public void compileTest(){
        String s = IOTools.exec("ls test | grep .java");
        String[] tests = s.split(" ");
        for(Project p : project_list) {
            if(p.isCorrect()){
                if(s.equals("")){
                    continue;
                }
                // only the last has a value
                String r = "?";
                for(String str : tests){
                    String cls = str.replaceAll(".java","");
                    String path = "Soumission/"+p.getName()+"/"+p.getName();
                    String log = IOTools.exec("cd test && cp "+str+" ../"+path+" && cd ../"+path+" && javac "+str+ " ");
                    String res = IOTools.exec("cd "+path +"java "+cls);
                    r = res.equals("") ? "?" : res;
                }
                p.setMethodTested(r);
            } else{
                p.setMethodTested("?");
            }
        }
    }

}
