package main.java.WordDocumentConcatenator.Indexing;

import main.java.WordDocumentConcatenator.StaticClasses.GroupingForSort;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class WriteTitlesToFile {
    private ArrayList<GroupingForSort> Groups;
    private String TargetDocument;
    private IndexValues ourValuesToIndex;

    public WriteTitlesToFile(ArrayList<GroupingForSort> Titles, String Target){
        this.Groups = Titles;
        this.TargetDocument = Target;
        this.ourValuesToIndex = new IndexValues();

    }
    public void Write() {
        try {
            File file = new File(this.TargetDocument);
            XWPFDocument document = new XWPFDocument(OPCPackage.open(file));
            for (GroupingForSort groups : Groups){
                ArrayList<TitleandPath> Titles = groups.grouped();
                XWPFParagraph GroupPar = document.createParagraph();
                XWPFRun grrun = GroupPar.createRun();
                if(groups.GroupClassification().compareTo("$")!=0)grrun.setText(groups.GroupClassification());
                //grrun.setColor("0000FF");
                //grrun.setUnderline(UnderlinePatterns.SINGLE);
                grrun.setFontSize(24);
                for (TitleandPath titleandPath : Titles) {
                    XWPFParagraph paragraph = document.createParagraph();
                    XWPFRun run = paragraph.createRun();
                    run.setText(titleandPath.title());
                    run.setColor("0000FF");
                    run.setUnderline(UnderlinePatterns.SINGLE);
                    ParagraphIndexLinker paragraphIndexLinker = new ParagraphIndexLinker(null, null);
                    this.ourValuesToIndex.addTitletoLink(titleandPath.title(), paragraphIndexLinker);
                    this.ourValuesToIndex.addIndexed(titleandPath.title());
                }
        }
            document.createParagraph().setPageBreak(true);

            FileOutputStream outputStream = new FileOutputStream(file,true);
            document.write(outputStream);
            outputStream.close();
            document.close();

        }catch (Exception e){
            System.out.println("Failed to write names in WriteTitlesToFileClass");
            e.printStackTrace();
        }
    }
    public IndexValues indexValues(){
        return this.ourValuesToIndex;
    }

}
