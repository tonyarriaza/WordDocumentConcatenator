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

public class TableShellCreatorCopier {
    private XWPFTable source;
    private XWPFTable target;
    public TableShellCreatorCopier(XWPFTable source, XWPFTable target){
        this.source = source;
        this.target = target;

    }
    public XWPFTable copyTableShell(){
        if(!this.source.getRows().isEmpty() && !this.target.getRows().isEmpty()){
            copyRow(source.getRow(0),target.getRow(0));
        }
        if (this.source.getTableAlignment()!=null){this.target.setTableAlignment(this.source.getTableAlignment());}
        this.target.setColBandSize(this.source.getColBandSize());
        this.target.setCellMargins(source.getCellMarginTop(),this.source.getCellMarginLeft(),this.source.getCellMarginBottom(),this.source.getCellMarginRight());
        this.target.setWidth(this.source.getWidth());
        List sourceRows = this.source.getRows();
        for(int i=0; i <sourceRows.size()-1;i++){
            XWPFTableRow currow =(XWPFTableRow)  sourceRows.get(i);
            copyRow(currow,this.target.createRow());

        }
        return this.target;

    }
    private void copyRow(XWPFTableRow source, XWPFTableRow target){
        if (!source.getTableCells().isEmpty() && !target.getTableCells().isEmpty()){
            copyCell(source.getCell(0),target.getCell(0));
        }
        target.getCtRow().setTrPr(source.getCtRow().getTrPr());
        target.setCantSplitRow(source.isCantSplitRow());

        target.setRepeatHeader(source.isRepeatHeader());
        List sourceCells = source.getTableCells();
        for(int i=0; i<sourceCells.size(); i++){
            if(target.getTableCells().size()==source.getTableCells().size()){
                break;
            }
            XWPFTableCell curcell = (XWPFTableCell ) sourceCells.get(i) ;
            copyCell(curcell,target.createCell());
        }

    }
    private void copyCell(XWPFTableCell source, XWPFTableCell target){
        //target.setWidthType(source.getWidthType());
        target.getCTTc().addNewTcPr().addNewTcW().setW(source.getCTTc().getTcPr().getTcW().getW());
        target.getCTTc().addNewTcPr().addNewNoWrap();
        target.setWidth(Integer.toString(source.getWidth()));

    }
}