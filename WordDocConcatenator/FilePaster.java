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
package main.java.WordDocumentConcatenator.WordDocConcatenator;
import main.java.WordDocumentConcatenator.Indexing.*;
import main.java.WordDocumentConcatenator.StaticClasses.GroupingForSort;
import main.java.WordDocumentConcatenator.StaticClasses.StaticMethods;
import main.java.WordDocumentConcatenator.TableCopyClasses.TableCopier;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;




import java.io.*;
import java.util.*;


public class FilePaster {
    private  ArrayList<String> Filenamer;
    private String directory;
    private File InputFile;
    private String[] Stringy;
    private int arrayPosition;
    private File[] bigFile;
    private ArrayList<File> FilestoConcatenate;
    private File theConcatenatedFile;
    private XWPFDocument target;
    private IndexValues indexValues;
    private String[][] OrderByRules;
    private ArrayList<TitleandPath> OrderedTitles;
    private ArrayList<GroupingForSort> GroupedFiles;


    public FilePaster(String directoryOfFilesToPaste,String nameForTheConcatonatedFile,String[]...OrderDefintions) throws Exception {
        this.directory = directoryOfFilesToPaste;

        this.InputFile = new File(directory);
        this.bigFile = new File[directory.length()];
        this.FilestoConcatenate= new ArrayList<File>();
        this.FilestoConcatenate= FileNames(directoryOfFilesToPaste);
        this.Filenamer=new ArrayList<String>();
        this.theConcatenatedFile= new newFileforConcatenation(directoryOfFilesToPaste +"\\" + nameForTheConcatonatedFile + ".docx").newFile();
        this.OrderByRules = OrderDefintions;


    }
    public void Concatenate(){
        ConcatenateFilesIntoASingleDocument();
    }


    private void ConcatenateFilesIntoASingleDocument() {
        try {
            this.GroupedFiles = writeNamesOfAllTheDocumentstoAnewocument(this.theConcatenatedFile,this.directory);
            this.target = new XWPFDocument(OPCPackage.open(this.theConcatenatedFile.getPath()));
            this.OrderedTitles = OrderedTitlesAndPaths(this.GroupedFiles);
            concatenation(this.OrderedTitles, this.target);

            File target = new File(this.theConcatenatedFile.getPath());
            FileOutputStream out = new FileOutputStream(target, true);
            this.target.write(out);
            out.close();
            this.target.close();
            Index index  = new Index(this.theConcatenatedFile.getAbsolutePath(),this.indexValues);
            index.generateIndeces();
            CreateFooter createFooter = new CreateFooter(this.theConcatenatedFile.getAbsolutePath());
            createFooter.create();
        }catch (Exception e){
            System.out.println("failed ConcatenateFileIntoASingleDocument");
        }

    }    //takes a string and a file, writes the contents of the string to the file
    private ArrayList<TitleandPath> OrderedTitlesAndPaths(ArrayList<GroupingForSort> list){
        ArrayList<TitleandPath> Titles = new ArrayList<TitleandPath>();
        for(GroupingForSort group :  list){
            if(group.grouped()!=null){
                ArrayList<TitleandPath> titleandPaths = group.grouped();
                for(TitleandPath titleandPath: titleandPaths){
                    Titles.add(titleandPath);
                }
            }
        }
        return Titles;
    }

    private ArrayList<GroupingForSort> writeNamesOfAllTheDocumentstoAnewocument(File newFile,String parentDir){
        XWPFDocument newDocument =null;
        OutputStream out =null;
        try{
            OrderTheFiles orderTheFiles =  new OrderTheFiles(this.directory,newFile.getAbsolutePath(),this.OrderByRules);

            ArrayList<GroupingForSort> titleandPaths = orderTheFiles.OrderedFiles();
           WriteTitlesToFile writeTitlesToFile =  new WriteTitlesToFile(titleandPaths,newFile.getAbsolutePath());
            writeTitlesToFile.Write();
            this.indexValues = writeTitlesToFile.indexValues();
            return  titleandPaths;

        } catch (Exception e){
            System.out.println("problem in writeNamesOfAllTheDocumentstoAnewocument");
            e.printStackTrace();
            return null;


        } finally {
            try{
                if(newDocument!=null) {
                    newDocument.close();
                }
                if(out!=null) {

                    out.close();
                }
            } catch (Exception e){
                System.out.println("writeNamesOfAllTheDocumentstoAnewocument");
            }
        }


    }
    // returns the name of the File
    private String nameOfGivenFile(File File){
        String result="";
        if(File.getName().contains(".doc")){
            result = File.getName().substring(0, File.getName().lastIndexOf(".doc"));
        }
        if(File.getName().contains(".rtf")){
            result = File.getName().substring(0, File.getName().lastIndexOf(".rtf"));
        }
        if(File.getName().contains(".txt")){
            result = File.getName().substring(0, File.getName().lastIndexOf(".txt"));
        }



        return result;
    }


    private File[] ListFiles(String Random) {

        this.InputFile = new File(Random);
        return this.InputFile.listFiles();

    }

    //appends the contents of one document to the second
    private void copyDocument(String fileToCopyPath, XWPFDocument fileToPasteTo) throws Exception{
        File fileToCopy = new File(fileToCopyPath);
        XWPFDocument theFileBeingCopied = null;
        OutputStream out=null;
        InputStream in=null;

        try{
            theFileBeingCopied = new XWPFDocument(OPCPackage.open(fileToCopy));

            if(!StaticMethods.landscape(theFileBeingCopied)) {
                XWPFParagraph orientpar = fileToPasteTo.createParagraph();
                StaticMethods.changeOrientation(orientpar,theFileBeingCopied);
                //orientpar = fileToPasteTo.createParagraph();
                //StaticMethods.changeOrientation(orientpar, theFileBeingCopied);

            }
            List newParagraphElements =fileToPasteTo.getBodyElements();
            List ListofParagraphs = theFileBeingCopied.getParagraphs();
            List listofElements = theFileBeingCopied.getBodyElements();
            List listofTables = theFileBeingCopied.getTables();
            List listOfPics = theFileBeingCopied.getAllPictures();
            for(int i=0;i<listofElements.size();i++){
                Object currentElement = listofElements.get(i);
                if(currentElement.getClass().equals(XWPFParagraph.class)){
                    XWPFParagraph oldPar = (XWPFParagraph) listofElements.get(i);
                    if(oldPar.getRuns().isEmpty()){
                        //XWPFParagraph par = fileToPasteTo.createParagraph();
                    }
                    if(!containsPic(oldPar)  && !oldPar.getRuns().isEmpty() && oldPar.getText().length()>0){
                       TextCopier textCopier = new TextCopier(oldPar,fileToPasteTo,theFileBeingCopied);
                        textCopier.copyText();
                    }
                    if(containsPic(oldPar)){
                      pictureCopier pictureCopier = new pictureCopier(oldPar,fileToPasteTo,directory,theFileBeingCopied);
                        pictureCopier.copyAllImages();
                    }

                }
                if(currentElement.getClass().equals(XWPFTable.class)){
                    XWPFTable currentTable = (XWPFTable) currentElement;
                    TableCopier copyTable = new TableCopier(currentTable,fileToPasteTo,theFileBeingCopied);
                    copyTable.copyTargetTable();
                }
            }

        }
        catch (Exception e){
            System.out.println("problem in copy document");
            System.out.println(e.getCause().getLocalizedMessage());
        }
        finally {

            try {
                XWPFParagraph par = fileToPasteTo.createParagraph();
                XWPFParagraph orientpar = fileToPasteTo.createParagraph();
                StaticMethods.changeOrientation(orientpar,theFileBeingCopied);
                if(!StaticMethods.landscape(theFileBeingCopied)) {
                    orientpar = fileToPasteTo.createParagraph();
                    StaticMethods.changeOrientation(orientpar, theFileBeingCopied);

                }
                theFileBeingCopied.close();


            } catch (Exception e) {
                System.out.println("theFileBeingCopied couldnt be closed");
                System.out.println(e.getCause().getLocalizedMessage());
            }
        }


    }
    public static boolean containsPic(XWPFParagraph currentPar){
        try {
            List parRuns = currentPar.getRuns();
            ListIterator iterator = parRuns.listIterator();
            while (iterator.hasNext()){
                XWPFRun currRun = (XWPFRun) iterator.next();
                if(!currRun.getEmbeddedPictures().isEmpty()){
                    return true;
                }
            }
        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    public static void copyRunText(XWPFRun currentRun,XWPFRun newRun){
        if(currentRun!=null ) {
            newRun.setFontSize(currentRun.getFontSize());

            String currentText = currentRun.text();
            newRun.setBold(currentRun.isBold());
            if(currentRun.getColor()!=null ){newRun.setColor(currentRun.getColor());}
            if(currentRun.getFontFamily()!=null ){newRun.setFontFamily(currentRun.getFontFamily());}
            newRun.setItalic(currentRun.isItalic());
            newRun.setCapitalized(currentRun.isCapitalized());
            newRun.setCharacterSpacing(currentRun.getCharacterSpacing());
            newRun.setDoubleStrikethrough(currentRun.isDoubleStrikeThrough());
            newRun.setEmbossed(currentRun.isEmbossed());

            newRun.setImprinted(currentRun.isImprinted());
            newRun.setKerning(currentRun.getKerning());
            if(currentRun.getLang()!=null ){newRun.setLang(currentRun.getLang());}
            newRun.setShadow(currentRun.isShadowed());
            newRun.setSmallCaps(currentRun.isSmallCaps());
            newRun.setStrikeThrough(currentRun.isStrikeThrough());
            if(currentRun.getSubscript()!=null) {newRun.setSubscript(currentRun.getSubscript());}

//        newRun.setVanish(currentRun.isVanish());

            //newRun.setVerticalAlignment("subscript");


            //if(currentRun.getVerticalAlignment()!=null){newRun.setVerticalAlignment("subscript");}
            if(currentRun.text()!= null) {newRun.setText(currentRun.text());}

        }

    }

    private void concatenation(ArrayList<TitleandPath> listOfFilesToBeConcatenated, XWPFDocument resultedConcatenation){

        try {
            for (TitleandPath i : listOfFilesToBeConcatenated) {
                copyDocument(i.path(), resultedConcatenation);
            }
        }
        catch(Exception e) {
            System.out.println("problem in concatenation");
            e.printStackTrace();
        }

    }


    //adds all of the files in the given directory into the field containing an arraylist of files
    private ArrayList<File> FileNames(String directory) {
        File[] directarray = ListFiles(directory);
        for (int i = 0; i < directarray.length; i++) {
            if (!directarray[i].isDirectory() && directarray[i].getPath().contains("docx")) {

                FilestoConcatenate.add(directarray[i].getAbsoluteFile());
                if (directarray[i].isDirectory()) {

                    directory = directarray[i].getAbsolutePath();

                    FileNames(directory);
                }
            }

        }
        return FilestoConcatenate;

    }

    private void IndexTheDocument(String path, IndexValues values){
        try {
            Index index = new Index(path, values);
            index.generateIndeces();
        }catch (Exception e){
            System.out.println("Failed IndexTheDocument");
        }

    }


}