package main.java.WordDocumentConcatenator.TableAlternative;

import org.apache.poi.xwpf.usermodel.*;

import java.util.List;
import java.util.ListIterator;

public class TableContentCopier {
    private XWPFTable source;
    private XWPFTable target;
    private String parentfolder;

    public TableContentCopier(XWPFTable source, XWPFTable target, String parentfolder){
        this.source= source;
        this.target = target;
        this.parentfolder = parentfolder;
    }

    public void copyContents(){
        List sourceRows = this.source.getRows();
        List targetRows = this.target.getRows();
        for(int i=0; i<sourceRows.size(); i++ ){
            XWPFTableRow sourceRow= (XWPFTableRow) sourceRows.get(i);
            XWPFTableRow targetRow = (XWPFTableRow) targetRows.get(i);
            copyRowContents(sourceRow,targetRow);
        }


    }

    private void copyRowContents(XWPFTableRow source, XWPFTableRow target){
        List sourceCells = source.getTableCells();
        List targetCells = target.getTableCells();
        for(int i=0; i<targetCells.size(); i++){
            XWPFTableCell sourceCell =  (XWPFTableCell) sourceCells.get(i);
            XWPFTableCell targetCell =  (XWPFTableCell) targetCells.get(i);
            copyCellContents(sourceCell,targetCell);
        }
    }
    private void copyCellContents(XWPFTableCell source, XWPFTableCell target){
        List sourcePars = source.getParagraphs();
        List targetPars = target.getParagraphs();
        for(int q=0; q<targetPars.size(); q++){
            target.removeParagraph(q);
        }
        for(int i=0; i<sourcePars.size();i++){
            if(sourcePars.get(i).getClass().equals(XWPFParagraph.class)){
                XWPFParagraph currPar = (XWPFParagraph) sourcePars.get(i);
                if(!containsPic(currPar)) {
                    CopyTableParagraphTextOnly copyPar = new CopyTableParagraphTextOnly(currPar, target);
                    copyPar.copyPar();
                }
                if(containsPic(currPar)){
                    CopyCellWithAPicture copyCellWithAPicture = new CopyCellWithAPicture(currPar,target,this.parentfolder);
                    copyCellWithAPicture.copyPics();

                }
            }
        }

    }
    private static boolean containsPic(XWPFParagraph currentPar){
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
}
