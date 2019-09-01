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
import main.java.WordDocumentConcatenator.WordDocConcatenator.FilePaster;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackagePart;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.*;
import java.math.BigInteger;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;





import javax.imageio.ImageIO;



public class pictureCopier {
    private XWPFParagraph oldPar;
    private XWPFDocument rec;
    private String dir;
    private  XWPFDocument oldDoc;

    public pictureCopier(XWPFParagraph oldPar, XWPFDocument rec, String directoryContainingDoc, XWPFDocument oldDoc) {
        this.oldPar = oldPar;
        this.rec = rec;
        this.dir = directoryContainingDoc;
        this.oldDoc = oldDoc;

    }

    public void copyAllImages() {
        int copyCounter = 0;
        XWPFRun runWithTex = null;
        boolean createdparagraph = false;
        XWPFParagraph newPar = null;
        try {
            List oldRuns = oldPar.getRuns();
            for (Object o : oldRuns) {
                XWPFRun currRun = (XWPFRun) o;
                if (currRun.text().length() > 1 && currRun.text() != null && runWithTex != null) {
                    XWPFParagraph lastPar = rec.createParagraph();
                    createdparagraph = true;
                    XWPFRun newRun = lastPar.createRun();;
                    FilePaster.copyRunText(runWithTex, newRun);
                    runWithTex = null;
                    lastPar.setPageBreak(false);
                }
                if (currRun.text().length() > 1 && currRun.text() != null && runWithTex == null) {
                    runWithTex = currRun;
                }
                if (!currRun.getEmbeddedPictures().isEmpty()) {
                    List pics = currRun.getEmbeddedPictures();
                    for (Object z : pics) {
                        XWPFPicture pic = (XWPFPicture) z;
                        int width = pic.getCTPicture().getSpPr().getXfrm().getExt().xgetCx().getBigIntegerValue().intValue();
                        int height = pic.getCTPicture().getSpPr().getXfrm().getExt().xgetCy().getBigIntegerValue().intValue();
                        appendImagetoDoc(pic,runWithTex,oldPar.getAlignment(),createdparagraph,width,height);
                        runWithTex = null;
                        copyCounter++;
                    }
                }if (copyCounter > 0) {

                    XWPFParagraph lastPar = (XWPFParagraph) rec.getBodyElements().get(rec.getBodyElements().size() - 1);
                    //lastPar.createRun();
                    XWPFRun newRun = lastPar.getRuns().get(lastPar.getRuns().size() - 1);

                    FilePaster.copyRunText(currRun, newRun);
                    //StaticMethods.changeOrientation(lastPar,this.oldDoc);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("problemo in copyAllImages");
        }


    }

    private void appendImagetoDoc(XWPFPicture pic, XWPFRun runText, ParagraphAlignment alignment, boolean hasnewpar, int wid, int hei) {
        try {
            if (hasnewpar) {
                XWPFParagraph recPar = (XWPFParagraph) this.rec.getBodyElements().get(this.rec.getBodyElements().size() - 1);
                recPar.setAlignment(alignment);
                XWPFRun recRun = recPar.createRun();
                FilePaster.copyRunText(runText, recRun);
                recRun.addPicture(new ByteArrayInputStream(pic.getPictureData().getData()),pic.getPictureData().getPictureType(),pic.getPictureData().getFileName(),wid,hei);
                if (recRun.text().length()<1){
                    recRun.setText("  ");
                }

                //StaticMethods.changeOrientation(recPar,this.oldDoc);
                return;
            }
            XWPFParagraph recPar = this.rec.createParagraph();
            recPar.setAlignment(alignment);
            XWPFRun recRun = recPar.createRun();
            FilePaster.copyRunText(runText, recRun);
            if (recRun.text().length()<1){
                recRun.setText("  ");
            }
            recRun.addPicture(new ByteArrayInputStream(pic.getPictureData().getData()),pic.getPictureData().getPictureType(),pic.getPictureData().getFileName(),wid,hei);
            recPar.setPageBreak(false);

            //StaticMethods.changeOrientation(recPar,this.oldDoc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




