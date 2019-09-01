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
