package main.java.WordDocumentConcatenator.Indexing;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Index {
    private XWPFParagraph source;
    private XWPFParagraph target;
    private int bookmarkId;
    private IndexValues values;

    private String path;
    private HashSet<String> AlreadyIndexed;

    public Index(String path, IndexValues indexValues) {
        try {
            this.bookmarkId = 0;
            this.values = indexValues;
            this.path = path;
            this.AlreadyIndexed = new HashSet<String>();
        }catch (Exception e){
            System.out.println("Failed to construct Index");
        }
    }
    public void generateIndeces() throws Exception {
        //appendExternalHyperlink("https://google.com", " Link to POI", document.createParagraph());
        MapLinksAndBookmarks(this.path,this.values);

    }

    static XWPFHyperlinkRun createHyperlinkRunToAnchor(XWPFParagraph paragraph, String anchor) throws Exception {
        CTHyperlink cthyperLink = paragraph.getCTP().addNewHyperlink();
        cthyperLink.setAnchor(anchor);
        cthyperLink.addNewR();
        return new XWPFHyperlinkRun(
                cthyperLink,
                cthyperLink.getRArray(0),
                paragraph
        );
    }
    static XWPFParagraph createBookmarkedParagraph(XWPFParagraph paragraph, String anchor, int bookmarkId) {
        CTBookmark bookmark = paragraph.getCTP().addNewBookmarkStart();
        bookmark.setName(anchor);
        bookmark.setId(BigInteger.valueOf(bookmarkId));
        XWPFRun run = paragraph.createRun();
        paragraph.getCTP().addNewBookmarkEnd().setId(BigInteger.valueOf(bookmarkId));
        return paragraph;
    }
    private void test(XWPFParagraph source,XWPFParagraph target) throws Exception{
        String anchor = "hyperlink_target"+bookmarkId;


        XWPFParagraph paragraph = source;


        //create hyperlink run
        XWPFHyperlinkRun hyperlinkrun = createHyperlinkRunToAnchor(paragraph, anchor);
        hyperlinkrun.setText(paragraph.getText());
        hyperlinkrun.setColor("0000FF");
        hyperlinkrun.setUnderline(UnderlinePatterns.SINGLE);

        //create bookmarked paragraph as the hyperlink target
        //XWPFParagraph target = targetPar(document);
        paragraph = createBookmarkedParagraph(target, anchor, bookmarkId++);
        //XWPFRun run = paragraph.getRuns().get(0);
        //run.setText("This is the target.");
    }
    private XWPFParagraph clear(XWPFParagraph paragraph){
        try{
            List<XWPFRun> runs = paragraph.getRuns();
            for(int i=0;i<runs.size();i++){
                paragraph.removeRun(i);
            }
            //System.out.println(paragraph.getText());
            return paragraph;

        }catch (Exception e){
            System.out.println("FailedToClear");
        }
        return paragraph;
    }


    private void MapLinksAndBookmarks(String path, IndexValues indexValues){
        try {
            int i=0;
            int q=0;
            boolean indexed=false;
            Iterator iterator = indexValues.theIndexed().iterator();
            while (iterator.hasNext()) {

                XWPFDocument document =new XWPFDocument(OPCPackage.open(path));
                XWPFParagraph[] linkvaluepar = new XWPFParagraph[2];

                List<XWPFParagraph> parList = document.getParagraphs();
                for (XWPFParagraph paragraph : parList) {
                    boolean hasbeenadded = false;
                    boolean newLink = false;

                    if (!this.AlreadyIndexed.contains(paragraph.getText()) && indexValues.hasBeenIndexed(paragraph.getText())) {


                        if (paragraph.getText().length() > 1 && indexValues.isATitle(paragraph.getText())) {
                          ParagraphIndexLinker paragraphIndexLinker = indexValues.getValue(paragraph.getText());
                            if (linkvaluepar[0] == null ) {
                                paragraphIndexLinker.changeLinked(paragraph);
                                linkvaluepar[0]=paragraph;
                                hasbeenadded = true;
                                q++;
                            }

                            if (!hasbeenadded && paragraphIndexLinker.Bookmarked() == null && paragraphIndexLinker.Link() != null &&!paragraph.equals(linkvaluepar[0]) ) {

                                linkvaluepar[1]=paragraph;
                                paragraphIndexLinker.changeBookmarked(paragraph);
                                this.AlreadyIndexed.add(paragraph.getText());
                                test(linkvaluepar[0], paragraph);
                                clear(linkvaluepar[0]);
                                File file = new File(this.path);
                                FileOutputStream fileOutputStream = new FileOutputStream(file, true);
                                document.write(fileOutputStream);
                                fileOutputStream.close();
                                document.close();
                                iterator.next();
                                indexed=true;
                                hasbeenadded=false;
                                break;

                            }
                        }

                    }
                }
                if(!indexed){
                    document.close();
                }
            }
        }catch (Exception e){
            System.out.println("Failed MapLinksAndBookmarks");
            e.printStackTrace();
        }

    }




    //source is the paragraph you want to index target is the paragraph you want to have the hyperlink




}
