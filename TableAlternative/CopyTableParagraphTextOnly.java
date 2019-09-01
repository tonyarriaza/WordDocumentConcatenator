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
