package main.java.WordDocumentConcatenator.Indexing;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.util.HashMap;
import java.util.HashSet;

public class IndexValues {
    private HashMap<String, ParagraphIndexLinker> TitlesToLink;
    private HashSet<String> AlreadyIndexed;
    private int numberOfIndexed;

    public IndexValues(){
        this.TitlesToLink = new HashMap<String, ParagraphIndexLinker>();
        this.AlreadyIndexed = new HashSet<String>();
        this.numberOfIndexed = 0;
    }

    public ParagraphIndexLinker getValue(String title){
        return this.TitlesToLink.get(title);

    }
    public ParagraphIndexLinker paragraphAndLinkPair(String contents){
        return  this.TitlesToLink.get(contents);
    }

    public void addIndexed(String freshlyIndexed){
        this.AlreadyIndexed.add(freshlyIndexed);
    }
    public void addTitletoLink(String name , ParagraphIndexLinker toLink){
        this.TitlesToLink.put(name,toLink);
    }

    public boolean isATitle(String isThisATitle){
        if(this.TitlesToLink.get(isThisATitle)!=null){
            return true;
        }
        return false;
    }
    public boolean hasBeenIndexed(String hasThisBeenIndexed){
        if(this.AlreadyIndexed.contains(hasThisBeenIndexed)){
            return true;
        }
        return false;
    }
    public int IndexCount(){
        return this.numberOfIndexed;
    }

    public void addIndexCount(){
        this.numberOfIndexed++;
    }
    public HashSet theIndexed(){
        return  this.AlreadyIndexed;
    }

}
