package main.java.WordDocumentConcatenator.Indexing;

public class DefinitionWeights {
    private String Definiton;
    private String Group;
    private int DefWeight;
    public DefinitionWeights (String Def,int DefWeight,String Group){
        this.Definiton = Def;
        this.DefWeight = DefWeight;
        this.Group = Group;
    }
    public String Definition(){
        return this.Definiton;
    }
    public int DefinitionWeight(){
        return DefWeight;
    }
    public String Group(){
        return this.Group;
    }

}
