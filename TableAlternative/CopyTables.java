package main.java.WordDocumentConcatenator.TableAlternative;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class CopyTables {
    private XWPFDocument source;
    private XWPFDocument target;
    private String dir;
    private String sourcePath;
    private String targetPath;
    private String parentfolder;


    public CopyTables(String sourceDoc, String TargetDoc, String parentfolder) {
        try {
            this.sourcePath = sourceDoc;
            this.targetPath = TargetDoc;
            File sourcefile = new File(this.sourcePath);
            File targetFile = new File(this.targetPath);
            this.source = new XWPFDocument(OPCPackage.open(sourcefile));
            this.target = new XWPFDocument(OPCPackage.open(targetFile));
            this.parentfolder = parentfolder;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copyTables() {
        try {


            List sourceTables = this.source.getBodyElements();
            for (int i = 0; i < sourceTables.size(); i++) {
                if (sourceTables.get(i).getClass().equals(XWPFTable.class)) {
                    XWPFTable currentTabl = (XWPFTable) sourceTables.get(i);
                    copyTable(currentTabl, this.target.createTable());
                }
            }


            File fileForWriting = new File(this.sourcePath);
            FileOutputStream out = new FileOutputStream(fileForWriting, true);
            this.target.write(out);
            out.close();
            this.source.close();
            this.target.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void copyTable(XWPFTable source, XWPFTable target) {
       TableShellCreatorCopier newShell = new TableShellCreatorCopier(source, target);
        XWPFTable tableshell = newShell.copyTableShell();
        copyTableContents(source, tableshell);

    }


    //WARNING:  THIS ONLY WORKS WITH AN EMPTY TABLE WITH THE SAME SHELL AS THE SOURCE
    private void copyTableContents(XWPFTable source, XWPFTable target) {
       TableContentCopier contentCopier = new TableContentCopier(source, target, this.parentfolder);
        contentCopier.copyContents();

    }
}

