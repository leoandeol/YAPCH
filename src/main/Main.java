import core.Test;
import data.Project;
import gui.HTMLOutput;
import util.IOTools;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args){
        Test test = new Test();
        if(args.length>=1 && args[0].equals("clean")){
            try {
                IOTools.cleanup();
            } catch (Exception e){
                e.printStackTrace();
            }
        } else {
            List<Project> projectList = test.runAllTests();
            try {
                HTMLOutput.render(projectList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
