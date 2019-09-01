package main.java.WordDocumentConcatenator.StaticClasses;

import com.microsoft.schemas.office.visio.x2012.main.FooterRightType;
import com.microsoft.schemas.office.visio.x2012.main.HeaderFooterType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;


import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;

public class StaticMethods {
//this Static method changes the orientation of the documents the reason it takes in a paragraph was because the only way I could get it to work is if each paragraphs orientation
    //was changed independantly in documents with alternating orientation issues might occur a solution may be to write some code that splits the documents in 2 based on their orientation
    //or this method could simply be edited to take in a source paragraph and copy the source paragraphs orientation
    //note all orientation changes MUST be done through the editing of the actual XML file using POI to do it will not work consistently therefore this method works SOLELY with the XML base
    public static void changeOrientation(XWPFParagraph par,XWPFDocument source){
        try {
            if (landscape(source)) {
                CTPPr ctpPr = par.getCTP().addNewPPr();
                par.getCTP().setPPr(ctpPr);
                CTSectPr sectPr = par.getCTP().getPPr().addNewSectPr();
                par.getCTP().getPPr().setSectPr(sectPr);
                CTPPrChange change = par.getCTP().getPPr().addNewPPrChange();
                par.getCTP().getPPr().setPPrChange(change);
                CTPageSz PageSize = par.getCTP().getPPr().getSectPr().addNewPgSz();
                par.getCTP().getPPr().getSectPr().setPgSz(PageSize);
                PageSize.setH(BigInteger.valueOf(12240));
                PageSize.setW(BigInteger.valueOf(15840));

            } else {
                CTPPr ctpPr = par.getCTP().addNewPPr();
                par.getCTP().setPPr(ctpPr);
                CTSectPr sectPr = par.getCTP().getPPr().addNewSectPr();
                par.getCTP().getPPr().setSectPr(sectPr);
                CTPPrChange change = par.getCTP().getPPr().addNewPPrChange();
                par.getCTP().getPPr().setPPrChange(change);
                CTPageSz PageSize = par.getCTP().getPPr().getSectPr().addNewPgSz();
                par.getCTP().getPPr().getSectPr().setPgSz(PageSize);
                PageSize.setH(BigInteger.valueOf(15840));
                PageSize.setW(BigInteger.valueOf(12240));


            }
        }catch (Exception e){
            System.out.println("failed to change paragraph orient");
        }

    }
    public static  boolean landscape(XWPFDocument xwpfDocument){
        try{
            String str = xwpfDocument.getDocument().getBody().getSectPr().getPgSz().getOrient().toString();
            if(str.contains("land")){
                return true;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

}
