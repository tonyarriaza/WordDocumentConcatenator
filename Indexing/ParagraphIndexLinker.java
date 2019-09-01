package main.java.WordDocumentConcatenator.Indexing;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

public class ParagraphIndexLinker {
    private XWPFParagraph Link;
    private XWPFParagraph Bookmarked;

    public ParagraphIndexLinker(XWPFParagraph link,XWPFParagraph Bookmarked){
        this.Link=link;
        this.Bookmarked = Bookmarked;
    }
    public XWPFParagraph Link(){
        return this.Link;
    }
    public XWPFParagraph Bookmarked(){
        return this.Bookmarked;
    }

    public void changeBookmarked(XWPFParagraph toMark){
        this.Bookmarked = toMark;
    }
    public void changeLinked(XWPFParagraph toLink){
        this.Link = toLink;
    }
}
