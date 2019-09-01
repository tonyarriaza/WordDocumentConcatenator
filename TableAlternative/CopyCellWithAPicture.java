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
package main.java.WordDocumentConcatenator.TableAlternative;

import org.apache.poi.xwpf.usermodel.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

public class CopyCellWithAPicture {
    private XWPFParagraph source;
    private XWPFTableCell target;
    private String parentfolder;

    public CopyCellWithAPicture(XWPFParagraph source, XWPFTableCell target, String parentfolder){
        this.source = source;
        this.target = target;
        this.parentfolder = parentfolder;
    }
    public void copyPics(){
        int copyCounter=0;
        XWPFRun runWithTex=null;
        XWPFParagraph newPar=null;
        boolean createdParagraph =false;
        try{
            List sourceRuns = this.source.getRuns();
            for(int i=0; i<sourceRuns.size();i++){
                XWPFRun currRun =  (XWPFRun) sourceRuns.get(i);
                if(currRun.text().length()>1 && currRun.text()!=null && runWithTex!=null){
                    XWPFParagraph lastPar = this.target.addParagraph();
                    lastPar.createRun();
                    createdParagraph = true;
                    XWPFRun newRun = lastPar.getRuns().get(lastPar.getRuns().size()-1);
                    CopyTableParagraphTextOnly.copyRunText(currRun,newRun);
                    runWithTex=null;


                }
                if(currRun.text().length()>1 && currRun.text()!=null && runWithTex==null){
                    runWithTex= currRun;
                }
                if(!currRun.getEmbeddedPictures().isEmpty()){
                    List pics  = currRun.getEmbeddedPictures();
                    for(Object z : pics){
                        XWPFPicture pic = (XWPFPicture) z;
                        BufferedImage bi = ImageIO.read(new ByteArrayInputStream((pic.getPictureData().getData())));
                        File tempFile = new File(parentfolder+"\\tempImage" +"." + pic.getPictureData().suggestFileExtension());
                        XWPFPictureData data = pic.getPictureData();
                        ImageIO.write(bi,data.suggestFileExtension(),tempFile);
                        int width = pic.getCTPicture().getSpPr().getXfrm().getExt().xgetCx().getBigIntegerValue().intValue();
                        int height = pic.getCTPicture().getSpPr().getXfrm().getExt().xgetCy().getBigIntegerValue().intValue();
                        int pictpe= data.getPictureType();
                        appendImagetoDoc(tempFile.getAbsolutePath(),runWithTex,this.source.getAlignment(),createdParagraph,width,height,pictpe);
                        tempFile.delete();
                        runWithTex= null;
                        copyCounter++;

                    }
                }
                if(copyCounter>0){
                    XWPFParagraph lastPar = (XWPFParagraph) this.target.getParagraphs().get(this.target.getParagraphs().size()-1);
                    XWPFRun newRun = lastPar.createRun();
                    CopyTableParagraphTextOnly.copyRunText(currRun,newRun);
                    copyCounter++;
                    runWithTex=null;

                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    private void appendImagetoDoc(String picPath,XWPFRun runText,ParagraphAlignment alignment, boolean hasnewpar,int wid,int hei,int pictype){
        try {
            if(hasnewpar){
                File pic = new File(picPath);
                XWPFParagraph recPar = this.target.getParagraphs().get(this.target.getParagraphs().size()-1);
                recPar.setAlignment(alignment);
                XWPFRun recRun = recPar.createRun();
                BufferedImage bimg1= ImageIO.read(pic);
                String imgFile = pic.getName();
                CopyTableParagraphTextOnly.copyRunText(runText,recRun);
                FileInputStream in = new FileInputStream(pic);
                recRun.addPicture(in,pictype,imgFile,wid,hei);
                return;
            }
            File pic = new File(picPath);
            XWPFParagraph recPar = target.addParagraph();
            recPar.setAlignment(alignment);
            XWPFRun recRun = recPar.createRun();
            BufferedImage bimg1 = ImageIO.read(pic);
            String imgFile = pic.getName();
            CopyTableParagraphTextOnly.copyRunText(runText,recRun);
            FileInputStream in = new FileInputStream(pic);
            recRun.addPicture(in,pictype,imgFile,wid,hei);
            bimg1.flush();
            in.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}