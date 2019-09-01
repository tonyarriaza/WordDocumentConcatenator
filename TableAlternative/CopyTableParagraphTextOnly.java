package main.java.WordDocumentConcatenator.TableAlternative;

import org.apache.poi.xwpf.usermodel.*;

import java.util.List;

public class CopyTableParagraphTextOnly {
    private XWPFParagraph oldPar;
    private XWPFTableCell target;
    public CopyTableParagraphTextOnly(XWPFParagraph oldPar, XWPFTableCell target){
        this.oldPar= oldPar;
        this.target = target;
    }

    public void copyPar(){
        XWPFParagraph newPar=  this.target.addParagraph();
        this.target.setParagraph(this.oldPar);
        XWPFParagraph setpar =  this.target.getParagraphs().get(this.target.getParagraphs().size()-1);
        List sourceRuns = this.oldPar.getRuns();

//        for(int i=0;i<sourceRuns.size();i++){
//            XWPFRun sourcerun = (XWPFRun) sourceRuns.get(i);
//            copyRunText(sourcerun,newPar.createRun());
//
//        }
    }
    public static void copyRunText(XWPFRun currentRun,XWPFRun newRun){
        if(currentRun!=null ) {

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
            if(currentRun.text()!= null) {newRun.setText(currentRun.text());}

        }

    }

}
