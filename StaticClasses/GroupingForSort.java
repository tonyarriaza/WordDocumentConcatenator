package main.java.WordDocumentConcatenator.StaticClasses;


import main.java.WordDocumentConcatenator.Indexing.DefinitionWeights;

import java.util.ArrayList;
import java.util.HashMap;

public class GroupingForSort {
    private ArrayList List;
    private String Definiton;
    private String GroupName;
    private HashMap Defweights;
    private int weight;


    public GroupingForSort (ArrayList list, String Definition, HashMap DefWeights){
        this.List = list;
        this.Definiton = Definition;
        this.Defweights = DefWeights;
        DefinitionWeights definitionWeights = (DefinitionWeights) this.Defweights.get(Definition);
        this.weight = definitionWeights.DefinitionWeight();
        this.GroupName = ((DefinitionWeights) this.Defweights.get(Definition)).Definition();
    }
    public ArrayList grouped(){
        return  this.List;
    }
    public String groupDefenition(){
        return  this.Definiton;
    }

    public String GroupClassification(){
        return this.GroupName;
    }
    public int getWeight(){
        return this.weight;
    }

}
