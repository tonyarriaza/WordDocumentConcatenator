/*Copyright [2019] [Tony Arriaza]

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.*/
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


