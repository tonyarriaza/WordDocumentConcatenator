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
package main.java.WordDocumentConcatenator.Indexing;

import main.java.WordDocumentConcatenator.StaticClasses.FileMethods;
import main.java.WordDocumentConcatenator.StaticClasses.GroupingForSort;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.xpath.operations.Or;


import java.io.File;
import java.util.*;

public class OrderTheFiles {
    private ArrayList<TitleandPath> Files;
    private String targetPath;
    private XWPFDocument Target;
    private String parentPath;
    private ArrayList<File> filesForPathReasons;
    private String[][] OrderValuesAndDef;
    private String[] Groupings;
    private String[] Defs;
    private HashMap<String,DefinitionWeights> DefinitionWeightMap;

    private  ArrayList<GroupingForSort> OrderedTitles;

    public OrderTheFiles(String parentPath,String targetPath,String[][] OrderValuesAndDef){
        try {
            this.parentPath = parentPath;

            this.filesForPathReasons = FileMethods.FileNames(this.parentPath);
            this.targetPath = targetPath;

            this.OrderValuesAndDef= OrderValuesAndDef;
            this.Defs = Definitions(this.OrderValuesAndDef);
            this.Groupings =groupings(this.OrderValuesAndDef);
            this.DefinitionWeightMap = OrderWeightMap(this.Defs,this.Groupings);
            this.OrderedTitles = Order();

            String x = "";



        }catch (Exception e){
            System.out.println("Failed to construct order");
            e.printStackTrace();

        }
    }
    public ArrayList<GroupingForSort> OrderedFiles(){
        return this.OrderedTitles;

    }
    private ArrayList<GroupingForSort> Order(){
        ArrayList correctlyOrdered =  new ArrayList<TitleandPath>();
        ArrayList OrderedByGroup = new ArrayList<GroupingForSort>();
        TitleandPath[] titleAndPaths = new TitleandPath[this.filesForPathReasons.size()];

        try{
            for(int i=0;i<this.filesForPathReasons.size(); i++){
                if(!this.filesForPathReasons.get(i).getAbsolutePath().equals(this.targetPath)) {
                    titleAndPaths[i] = fileTitleAndPath(this.filesForPathReasons.get(i));

                }
            }


            for(int i=0;i<titleAndPaths.length;i++){
                if(titleAndPaths[i]!=null) {
                    correctlyOrdered.add(titleAndPaths[i]);
                    //System.out.println(titleAndPaths[i].title());
                }
            }


           Collections.sort(correctlyOrdered,new TitleCompare());
            OrderedByGroup = createGroupSortedList(correctlyOrdered,this.Defs);
            String s = "";

        }catch (Exception e){
            System.out.println("Failed to return arraylist of OrderedFiles");
            e.printStackTrace();
        }
        return OrderedByGroup;
    }
    private TitleandPath fileTitleAndPath(File path){
      TitleandPath titlenpath= null;
        try{
            XWPFDocument document = new XWPFDocument(OPCPackage.open(path.getAbsolutePath()));
            List<XWPFParagraph> pars = document.getParagraphs();
            if(pars.size()>=2 &&pars.get(1).getClass().equals(XWPFParagraph.class)){
                XWPFParagraph paragraph = pars.get(1);
                String Title = paragraph.getText();
                titlenpath = new TitleandPath(path.getAbsolutePath(),Title);
                document.close();
            }

        }catch (Exception e){
        }
        return titlenpath;
    }

    private String[] Definitions(String[][] Defs){
        String[] holder = new String[Defs[0].length];
        String[] Definitions  =new String[Defs[0].length/2];
        for(int i=0;i< holder.length;i++){
            if(i%2 ==0){
                holder[i]= Defs[0][i];
            }
        }
        int refill = 0;
        int holderIndex = 0;
        while(refill<Definitions.length){
            if(holderIndex>=holder.length) break;
            if(holder[holderIndex]!=null){
                Definitions[refill] = holder[holderIndex];
                refill++;
                //holderIndex++;
            }
            holderIndex++;
        }
        String[] newArray = new String[Definitions.length+1];
        for(int i=0;i<Definitions.length;i++){
            newArray[i]=Definitions[i];
        }
        newArray[newArray.length-1] = "$";
        Definitions = newArray;

        return Definitions;
    }
    private String[] groupings(String[][] Defs){
        String[] holder = new String[Defs[0].length];
        String[] Definitions  =new String[Defs[0].length/2];
        for(int i=0;i< holder.length;i++){
            if(i%2 !=0){
                holder[i]= Defs[0][i];
            }
        }
        int refill = 0;
        int holderIndex = 0;
        while(refill<Definitions.length){
            if(holderIndex>=holder.length) break;
            if(holder[holderIndex]!=null){
                Definitions[refill] = holder[holderIndex];
                refill++;
                //holderIndex++;
            }
            holderIndex++;
        }
        String[] newArray = new String[Definitions.length+1];
        for(int i=0;i<Definitions.length;i++){
            newArray[i]=Definitions[i];
        }
        newArray[newArray.length-1] = "$";
        Definitions = newArray;

        return Definitions;
    }
     class TitleCompare implements Comparator<TitleandPath> {

        public int compare(TitleandPath a,TitleandPath b){
            int x = 0;
            if(a==null && b!=null){
                return -1;
            }
            if(a!=null && b==null){
                return 1;
            }
            if(a==null && b==null){
                return 0;


            }
            return a.title().compareTo(b.title());




            //return x;


        }
    }
    private ArrayList<GroupingForSort> createGroupSortedList(ArrayList<TitleandPath> toGroup, String[] Order){
        ArrayList<GroupingForSort> groupingForSorts = new ArrayList<GroupingForSort>();
        for(String OrdererName:Order){
            groupingForSorts.add(new GroupingForSort(new ArrayList<TitleandPath>(),OrdererName,this.DefinitionWeightMap));
        }
        for(int i=0;i<toGroup.size();i++){
            TitleandPath titleandPath= toGroup.get(i);
            if(this.DefinitionWeightMap.get(inTheMap(this.DefinitionWeightMap,titleandPath.title())).DefinitionWeight()== groupingForSorts.size()){
                GroupingForSort group = groupingForSorts.get(this.DefinitionWeightMap.get(inTheMap(this.DefinitionWeightMap,titleandPath.title())).DefinitionWeight()-1);
                group.grouped().add(titleandPath);
                System.out.println(group.grouped());
            }else {
                GroupingForSort group = groupingForSorts.get(this.DefinitionWeightMap.get(inTheMap(this.DefinitionWeightMap, titleandPath.title())).DefinitionWeight());
                group.grouped().add(titleandPath);
            }


        }
        return groupingForSorts;

    }
    private String inTheMap(HashMap<String,DefinitionWeights> map ,String string){
        if(string==null){
            return "$";
        }
        for(String str:map.keySet()){
            if (string.contains(str) && string.length()>1) return str;
        }
        return "$";
    }

    private HashMap<String,DefinitionWeights> OrderWeightMap(String[] Def,String[] Order){
        HashMap hashMap = new HashMap();
        for(int i=0;i<Def.length;i++){
            DefinitionWeights definitionWeights =  new DefinitionWeights(Order[i],i,Def[i]);
            hashMap.put(Def[i],definitionWeights);
        }
        hashMap.put("$",new DefinitionWeights("$",Order.length,"$"));
        return hashMap;
    }
    private int setWeight(String[] defs,String definiton){
        for(int i=0;i<defs.length;i++){
            if(defs[i].compareTo(definiton)==0){
                return i;
            }
        }
        return -1;
    }

}


