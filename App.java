
package main.java.WordDocumentConcatenator;

//import main.java.testcopier.FilePaster;


import main.java.WordDocumentConcatenator.WordDocConcatenator.FilePaster;

public class App
{
    public static void main(String[] args ) throws  Exception
    {
        final long startTime = System.currentTimeMillis();

      FilePaster copyDoc = new FilePaster("C:\\Users\\yourPathHere","Concant",new String[]{"16.2","Listing","14.2","Efficacy","14.1","Demographics"});
      copyDoc.Concatenate();
        final long endTime = System.currentTimeMillis();
        final long secondtime= (endTime-startTime)/1000;

        System.out.println("Total execution time: " + (secondtime) + " Seconds");
    }
}
