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
