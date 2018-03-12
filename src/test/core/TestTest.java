package core;

import data.Project;
import exception.NotAFolderException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import util.FileTools;
import util.IOTools;
import util.ZipTools;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static org.junit.Assert.*;

public class TestTest {

    core.Test t;

    @Before
    public void setUp() throws Exception {
        t = new core.Test();
        t.findProjectsInSubmissions();
        t.checkProjects();
    }

    @After
    public void tearDown() throws Exception {
    }

    @AfterClass
    public static void cleanUp() throws Exception {
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

    @Test
    public void runAllTests() {
    }

    //fix
    @Test
    public void findProjectsInSubmissions() {
        for (Project p : t.getProject_list()) {
            if (p.getName().equals("AsterixObelix")) {
                return;
            }
        }
        fail();
    }

    @Test
    public void checkProjects() {
        assertTrue(t.getProject_list().get(0).isCorrect());
    }

    @Test
    public void compileProjects(){
        t.compileProjects();
        assertTrue(t.getProject_list().get(0).isCompiles());
    }

    @Test
    public void compileJavadoc(){
        t.compileJavadoc();
        assertTrue(t.getProject_list().get(0).getJavadocTested()== Project.State.NOTAVAILABLE);
    }

    @Test
    public void compileUnitTest(){
        t.compileUnitTest();
        assertTrue(t.getProject_list().get(0).getUnitTested() == Project.State.BROKEN);
    }

    @Test
    public void compileTest(){
        t.compileTest();
        assert !t.getProject_list().get(0).getMethodTested().equals("?");
    }
}