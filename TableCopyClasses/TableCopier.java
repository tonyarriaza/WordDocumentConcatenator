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
package main.java.WordDocumentConcatenator.TableCopyClasses;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTPages;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;




public class TableCopier {
    private XWPFTable source;
    private XWPFDocument rec;
    private String dir;
    private XWPFTable target;
    private  XWPFDocument oldDoc;
    public TableCopier (XWPFTable oldtable, XWPFDocument rec, XWPFDocument oldDoc){
        this.source = oldtable;
        this. rec = rec;
        this.oldDoc = oldDoc;
    }
    public void copyTargetTable(){
        try {
            //StaticMethods.changeOrientation(this.rec.createParagraph(),oldDoc);
            this.target =rec.createTable();
            copyTable(this.source,this.target);
            //StaticMethods.changeOrientation(this.rec.createParagraph(),oldDoc);
        }catch ( Exception e){
            System.out.println("failed tables and stuff");
        }
    }
    private void copyTable(XWPFTable source, XWPFTable target) {
        target.setStyleID(source.getStyleID());
        target.getCTTbl().setTblPr(source.getCTTbl().getTblPr());
        target.getCTTbl().setTblGrid(source.getCTTbl().getTblGrid());
        CTTblPPr ctTblPPr = target.getCTTbl().getTblPr().getTblpPr();

        for (int r = 0; r<source.getRows().size(); r++) {
            XWPFTableRow targetRow = target.createRow();
            XWPFTableRow row = source.getRows().get(r);
            targetRow.getCtRow().setTrPr(row.getCtRow().getTrPr());
            for (int c=0; c<row.getTableCells().size(); c++) {
                //newly created row has 1 cell
                XWPFTableCell targetCell = c==0 ? targetRow.getTableCells().get(0) : targetRow.createCell();
                XWPFTableCell cell = row.getTableCells().get(c);
                targetCell.getCTTc().setTcPr(cell.getCTTc().getTcPr());
                XmlCursor cursor = targetCell.getParagraphArray(0).getCTP().newCursor();
                for (int p = 0; p < cell.getBodyElements().size(); p++) {
                    IBodyElement elem = cell.getBodyElements().get(p);
                    if (elem instanceof XWPFParagraph) {
                        XWPFParagraph targetPar = targetCell.insertNewParagraph(cursor);
                        cursor.toNextToken();
                        XWPFParagraph par = (XWPFParagraph) elem;
                        copyParagraph(par, targetPar);
                        par.setPageBreak(false);
                        //StaticMethods.changeOrientation(targetPar,this.oldDoc);
                    } else if (elem instanceof XWPFTable) {
                        XWPFTable targetTable = targetCell.insertNewTbl(cursor);
                        XWPFTable table = (XWPFTable) elem;
                        copyTable(table, targetTable);
                        cursor.toNextToken();
                    }
                }
                //newly created cell has one default paragraph we need to remove
                targetCell.removeParagraph(targetCell.getParagraphs().size()-1);
            }
        }
        //newly created table has one row by default. we need to remove the default row.
        target.removeRow(0);
    }
    private void copyParagraph(XWPFParagraph source, XWPFParagraph target) {
        target.getCTP().setPPr(source.getCTP().getPPr());
        for (int i=0; i<source.getRuns().size(); i++ ) {
            XWPFRun run = source.getRuns().get(i);
            XWPFRun targetRun = target.createRun();
            //copy formatting
            targetRun.getCTR().setRPr(run.getCTR().getRPr());
            //no images just copy text
            targetRun.setText(run.getText(0));
        }
    }

}

