package exception;

public class NotAFolderException extends Exception {

    public NotAFolderException(){
        super("An unknown file isn't a folder");
    }

    public NotAFolderException(String file){
        super(file + " is not a folder");
    }

}
