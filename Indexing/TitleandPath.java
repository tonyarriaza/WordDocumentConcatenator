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
