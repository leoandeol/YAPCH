import core.Test;
import data.Project;

import java.util.List;

public class Main {

    public static void main(String[] args){
        Test test = new Test();
        List<Project> projectList= test.runAllTests();
    }
}
