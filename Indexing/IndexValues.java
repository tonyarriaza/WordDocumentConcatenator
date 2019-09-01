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
