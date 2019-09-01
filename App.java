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
package main.java.WordDocumentConcatenator;

//import main.java.testcopier.FilePaster;


import main.java.WordDocumentConcatenator.WordDocConcatenator.FilePaster;

public class App
{
    public static void main(String[] args ) throws  Exception
    {
        final long startTime = System.currentTimeMillis();

      FilePaster copyDoc = new FilePaster("C:\\Users\\arria\\Documents\\SampleInputs","Concant",new String[]{"16.2","Listing","14.2","Efficacy"});
      copyDoc.Concatenate();
        final long endTime = System.currentTimeMillis();
        final long secondtime= (endTime-startTime)/1000;

        System.out.println("Total execution time: " + (secondtime) + " Seconds");
    }
}
