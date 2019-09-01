package main.java.WordDocumentConcatenator.Indexing;


import java.io.File;

//this class just serves as a way to keep track of each documents title so we can order them correctly using our trusty mergesort found in the static classes although maybe it should be elsewhere
public class TitleandPath {
    private String path;
    private String title;


    public TitleandPath(String path, String title){
        this.path=path;
        this.title=title; }
    public String path(){
        return this.path;
    }
    public String title(){
        return this.title;
    }

}
