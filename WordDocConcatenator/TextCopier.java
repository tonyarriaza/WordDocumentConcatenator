package main.java.WordDocumentConcatenator.WordDocConcatenator;
import main.java.WordDocumentConcatenator.Indexing.IndexValues;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.sl.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.ImageUtils;
import org.apache.poi.util.Units;
import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.openxml4j.opc.PackagePart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.jar.Pack200;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalAlignRun;

//import sun.tools.jstat.Alignment;

import javax.imageio.ImageIO;


public class TextCopier {
    private XWPFParagraph oldPar;
    private XWPFDocument rec;
    private String dir;
    private XWPFDocument oldDoc;
    private IndexValues checkForIndexing;
    public TextCopier (XWPFParagraph oldpar, XWPFDocument rec, XWPFDocument oldDoc){
        this.oldPar = oldpar;
        this. rec = rec;
        this.oldDoc = oldDoc;

    }
    public void copyText(){
        try {
            XWPFParagraph npar =this.rec.createParagraph();
            List listOfRecDocPars = this.rec.getParagraphs();
            this.rec.setParagraph(this.oldPar, listOfRecDocPars.size() - 1);
            XWPFParagraph par = rec.getLastParagraph();
            //StaticMethods.changeOrientation(par,this.oldDoc);

        }catch ( Exception e){
            e.printStackTrace();
        }
    }
}

