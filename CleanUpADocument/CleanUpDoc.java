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
package main.java.WordDocumentConcatenator.CleanUpADocument;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;


import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CleanUpDoc {
    private String path;
    private XWPFDocument document;
    public CleanUpDoc(String path){
        try {
            this.path = path;
            this.document = new XWPFDocument(OPCPackage.open(path));
        }catch (Exception e){
            System.out.println("failed cleanup construct");
        }
    }

    public void cleanUp(){
        try {
            clean(this.document);
            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            this.document.write(fileOutputStream);
            this.document.close();
            fileOutputStream.close();
        }catch (Exception e){
            System.out.println("Failed cleanUp");
        }

    }
    private void clean(XWPFDocument document){
        List list= document.getBodyElements();

        for(int i=0;i<list.size();i++) {
            if (list.get(i).getClass().equals(XWPFParagraph.class)) {
                XWPFParagraph paragraph = (XWPFParagraph) list.get(i);
                if(paragraph.getText().length()<1){
                    System.out.println(paragraph);
                }
                if (paragraph.getText().length() < 2 && (!paragraph.getText().contains("ORIENT CHANGED") || !paragraph.getText().contains("PAGEBREAKGENERATED"))) {
                    document.removeBodyElement(i);
                }
            }
        }

    }
}
