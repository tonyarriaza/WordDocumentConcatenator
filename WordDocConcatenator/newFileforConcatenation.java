package main.java.WordDocumentConcatenator.WordDocConcatenator;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;

// the purpose of this class is to generate an empty doc file to be used in our main class
// the reason for using a seperate class is for clarity as well as ensuring we create a new file every time and don't write over existing files
public class newFileforConcatenation{
    private File newFile;
    private String nameForFile;
    private XWPFDocument newDoc;

    public newFileforConcatenation(String FilenameandDirectory){
        try{
            newDoc = new XWPFDocument();
            newFile = new File(FilenameandDirectory);
            newFile.createNewFile();
            this.nameForFile= FilenameandDirectory;
            newDoc.write(new FileOutputStream(newFile));
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("if the stack trace is printed that means the author of this code forgot to change how exceptions were handled");
        }

    }
    public File newFile(){
        return newFile;
    }
    public String newFilesDirectory(){
        return nameForFile;
    }
    public String nameOfTheNewFile(){
        return newFile.getName().substring(0, newFile.getName().lastIndexOf(".doc"));
    }
    public XWPFDocument newWordDoc(){
        return newDoc;
    }
}
