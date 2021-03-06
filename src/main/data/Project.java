package data;

public class Project {

    private String name;
    /**
     * Check if the zip is in the correct format
     */
    private boolean correct;
    /**
     * Check if the project compiles
     */
    private boolean compiles;
    /**
     * checks the score according to the test given by the teacher
     */
    private String methodTested;
    /**
     * checks if there is javadoc in the code and if it's valid
     */
    private State javadocTested;
    /**
     * checks if the unit tests run smoothly
     */
    private State unitTested;
    public Project(String name) {
        this.name = name;
        this.compiles = false;
        this.correct = false;
        this.methodTested = "?";
        this.javadocTested = State.UNKNOWN;
        this.unitTested = State.UNKNOWN;
    }

    public boolean isCompiles() {
        return compiles;
    }

    public void setCompiles(boolean compiles) {
        this.compiles = compiles;
    }

    public State getUnitTested() {
        return unitTested;
    }

    public void setUnitTested(State unitTested) {
        this.unitTested = unitTested;
    }

    public State getJavadocTested() {
        return javadocTested;
    }

    public void setJavadocTested(State javadocTested) {
        this.javadocTested = javadocTested;
    }

    public String getMethodTested() {
        return methodTested;
    }

    public void setMethodTested(String methodTested) {
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

    public enum State {
        UNKNOWN,
        AVAILABLE,
        NOTAVAILABLE,
        WORKING,
        BROKEN
    }
}
