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
package main.java.WordDocumentConcatenator.Indexing;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;

public class CreateFooter {
    private XWPFDocument doc;
    private String path;

    public CreateFooter(String path) {
        try {


            this.path = path;
            this.doc = new XWPFDocument(OPCPackage.open(path));
        }catch (Exception e){
            System.out.println("Failed to foot :(");
        }
    }


    public void create() {
        try {

            // create footer


            XWPFHeaderFooterPolicy policy = doc.createHeaderFooterPolicy();
            doc.createFootnotes();
            doc.createNumbering();

            CTP ctpFooter = CTP.Factory.newInstance();

            XWPFParagraph[] parsFooter;

// add style (s.th.)
            CTPPr ctppr = ctpFooter.addNewPPr();
            ctpFooter.setPPr(ctppr);
            CTString pst = ctppr.addNewPStyle();
            pst.setVal("style21");
            CTJc ctjc = ctppr.addNewJc();

            ctjc.setVal(STJc.RIGHT);
            ctppr.setJc(ctjc);
            CTParaRPr ctpPr = ctppr.addNewRPr();
            ctppr.setRPr(ctpPr);

// Add in word "Page "
            CTR ctr = ctpFooter.addNewR();
            CTText t = ctr.addNewT();
            t.setStringValue("Page ");
            t.setSpace(SpaceAttribute.Space.PRESERVE);

// add everything from the footerXXX.xml you need
            ctr = ctpFooter.addNewR();
            ctr.addNewRPr();
            CTFldChar fch = ctr.addNewFldChar();
            fch.setFldCharType(STFldCharType.BEGIN);

            ctr = ctpFooter.addNewR();
            ctr.addNewInstrText().setStringValue(" PAGE ");

            ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.SEPARATE);

            ctpFooter.addNewR().addNewT().setStringValue("1");

            ctpFooter.addNewR().addNewFldChar().setFldCharType(STFldCharType.END);

            XWPFParagraph footerParagraph = new XWPFParagraph(ctpFooter, doc);

            parsFooter = new XWPFParagraph[1];

            parsFooter[0] = footerParagraph;

            policy.createFooter(XWPFHeaderFooterPolicy.FIRST, parsFooter);
            doc.createFooter(HeaderFooterType.FIRST);
            doc.createFootnote().ensureFootnoteRef(footerParagraph);

            File file =  new File(this.path);
            FileOutputStream out = new FileOutputStream(file,true);
            this.doc.write(out);
            this.doc.close();
            out.close();
        } catch (Exception e) {
            System.out.println("failed to foot");
        }
    }
}
