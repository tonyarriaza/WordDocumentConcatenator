package main.java.WordDocumentConcatenator.StaticClasses;

import java.io.File;
import java.util.ArrayList;

public class FileMethods {
    private static File[] ListFiles(String dir) {

    File InputFile = new File(dir);
    return InputFile.listFiles();

}
    public static ArrayList<File> FileNames(String directory) {
        File[] directarray = ListFiles(directory);
        ArrayList listOfFiles=  new ArrayList<File>();
        for (int i = 0; i < directarray.length; i++) {
            if (!directarray[i].isDirectory() && directarray[i].getPath().contains("docx")) {


                listOfFiles.add(directarray[i].getAbsoluteFile());
                if (directarray[i].isDirectory()) {

                    directory = directarray[i].getAbsolutePath();

                    FileNames(directory);
                }
            }

        }
        return listOfFiles;

    }


}


