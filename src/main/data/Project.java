package data;

public class Project {
    private String name;
    /**
     * Check if the zip is in the correct format
     */
    private boolean correct;
    /**
     * check if the methods are correct following the test class given by the teacher
     */
    private char methodTested;
    /**
     * checks if there is javadoc in the code and if it's valid
     */
    private char javadocTested;
    /**
     * checks if the unit tests run smoothly
     */
    private char unitTested;


    public Project(String name){
        this.name=name;
    }

    public char getUnitTested() {
        return unitTested;
    }

    public void setUnitTested(char unitTested) {
        this.unitTested = unitTested;
    }

    public char getJavadocTested() {
        return javadocTested;
    }

    public void setJavadocTested(char javadocTested) {
        this.javadocTested = javadocTested;
    }

    public char getMethodTested() {
        return methodTested;
    }

    public void setMethodTested(char methodTested) {
        this.methodTested = methodTested;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getName() {
        return name;
    }
}
