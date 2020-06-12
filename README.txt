Potential Contributions for the Open Source Project Apache POI
Author: Tony Arriaza
I write this documentation in the hopes that I can contribute towards the apache foundations POI project, below is a list of classes I have developed and their potential usefulness for the project.
I have written a piece of software that can take an input of a folder directory and parse through all of the files, find any Microsoft word documents then the software concatenates and indexes each file into a brand new file that begins a table of contents with hyperlinks to each relevant file. The software allows for user input of user defined groupings and definitions and sorts based on these groupings and definitions, this however is not required of the user and serves as an additional option. 
Purpose: In the Clinical Research Drug Development industry the CSR (Clinical Study Report) is submitted to Regulatory Agencies, for potential approval of the sponsor drug. These reports need to adhere to certain critical Industry standards and guidelines. The Biometrics department is responsible to produce individual outputs (Tables, Listings and Figures), that are utilized in writing the CSR. Some of these outputs are attached as appendix in the CSR document, and hence is the need to concatenate all of the outputs into a single file, and this utility will help facilitate the process by automating certain activities, to manage the process more efficiently.
Here is an index of each class: 
Mainmethod
FilePaster
TextCopier Class
newFileforConcatenation Class
pictureCopier Class
TableCopierClass
IndexClass
IndexValues 
ParagraphIndexLinker Class
TitleandPath
WriteTitlesToFile
GroupingForSort Class
StaticMethods
Omitted Classes
Note: when referring to index within this document these links are an example of what I am referring to
The MainMethod
The main method uses the FilePaster class which takes an input of a path, the name of the new document followed by arguments that define groupings and the group names.
The FilePaster Class
It should be noted that the FilePaster Class does most of the work for the project and incorporates every other class in this document. Therefore I will explain the purpose of each method.
FilePaster Constructor
The main purpose of the constructor is to build lists to hold the documents that must be concatenated as well as create a brand new Microsoft Word document that will have all of the documents concatenated into.
Concatenate Public Method
This is the public method that calls the private method which does all of the work of concatenating the documents.
ConcatenateFilesIntoASingleDocument Method
This is the private method that does the entirety of the work of the class. It uses the writeNamesOfAllTheDocumentstoAnewocument method which writes the titles of all of the documents in the input folder to a brand new word document, this method returns an arraylist of all of the documents sorted and grouped based on the user input provided in the constructor. The method then proceeds to assign our this.target field by using apache poi’s OPCpackage.open method so the program can continuously edit the new document for the rest of the time it is running. Note the new document is created in the writeNamesOfAllTheDocumentstoAnewocument however as this method actually writes to a file we have to open up the document again. The program then sets the this.OrderedTitles field which ensures our copying is done in the correct order by only holding the actual titles and their respective file paths. Up next the actual Concatenation is done through the Concatenation method this method iterates through the actual files and copies and concatenates them into our new word document. The method then proceeds to write the new data that was concatenated to the actual file of the word document over the next five lines of code. Once the concatenation has been written to a file the method creates a new Index class to prepare for the indexing portion of the software using the file path of our new document and the this.indexvalues field which was created as we concatenated to keep track of each documents paragraph that needs to be indexed. Further elaboration can be found in the documentation of the Index class.
OrderedTitlesAndPaths Method
This method simply takes an arraylist of user defined groups and the files that belong to each group and produces a new arraylist that is properly ordered for concatenation so we don’t have to continuously keep track of groups as we go.
writeNamesOfAllTheDocumentstoAnewocument Method
Meant to be writeNamesOfAllTheDocumentstoAnewDocument however due to a typo that needs to be fixed the proper name of the method is not yet updated. This takes the user defined folder that contains the documents to be concatenated as its first parameter, and the path of said folder in a string version as its second parameter. This was done for compatibility reasons and can easily be modified to only take the String path. It begins by creating a new instance of the OrderTheFiles class which shares the first two parameters in its construction as the writeNamesOfAllTheDocumentstoAnewocument method however it requires the user defined Order rules however if these are empty it’s a non-issue. The method then creates an arraylist of the grouped files using the OrderedFiles method from the OrderTheFiles class. Further elaboration can be found in the class’s respective documentation later in this document. The method then creates an instance of the WriteTitlesToFile class which takes the arraylist of our grouped files and a file path of the files whose titles we will be writing to our new word document. It then performs the write method which is explained in the WriteTitlesToFile documentation. The method then sets the this.indexvalues field so we can use them to index the documents accordingly later. The rest of the method is exception handling and making sure our files are all cleaned up.
nameOfGivenFile Method
Unused in current iteration but serves as a way of stripping extensions from certain file types.
ListFiles Method
Creates a File array from a directory path, requires a string that serves as a directory path as input.
copyDocument Method
This method takes an instance of an XWPFDocument class from apache poi and a String of the path of a document we wish to copy our instance. The purpose of this method is to append the entirety of one document to another. The document that gets edited is the XWPFDocument instance and the document that gets copied over is the document with the path given by the first parameter. This method begins by creating a File instance from the first parameter it then creates a new XWPFDocument instance from this file so that our program can copy its data effectively. The document that is being copied then has its orientation checked if the orientation is portrait mode we then ensure that the next portion of our document that’s receiving the copy remains in portrait mode. This is done by creating a new paragraph in the document to be updated then the program uses the StaticMethods.changeOrientation method to properly set the orientation going forward. The program then creates a list of all the elements in the document to be copied and begins a for loop of all the elements. Each element is then checked to be either a paragraph with no pictures, a paragraph with pictures, a table, or an empty paragraph. Depending on what type of object the element is a new instance of either the TextCopier pictureCopier or TableCopier classes. Each classes respective copy method is called and copied over to the XWPFDocument instance that represents the document that is to receive the copy. Once the loop is finished we check for any exceptions then ensure that the documents orientation is properly set whether that is Landscape or Portrait. It is important to note that a document that goes back and forth between portrait and landscape is currently not supported as the software will pick the orientation that it finds on the first page. This can be changed but will result in an additional page being added each time the orientation is changed. The method then ends by closing the XWPFDocument instance we were copying from and checks for further exceptions.
containsPic Method
This method iterates over a paragraph in a word document and checks if the paragraph as a picture in it, it is used in the copyDocument method as part of the Boolean logic that helps the method determine which copy methods to use.
copyRunText Method
This method is a static method that is used throughout the rest of the software and simply copies the text of a certain portion of a paragraph including that portions text color, size, and whether it’s underlined, in bold , or in italics. Its parameters are the portion of a paragraph that needs to be copied and a portion of another paragraph that will receive the copy.
Concatenation Method
This method takes a list of a folder titles and its paths as well as an XWPFDocument instance that you want to concatenate into. It uses a for loop over the list and passes each path and the XWPFDocument instance to the copyDocument method. It finishes with exception checking.
FileNames Method
This method takes a folder path as a string iterates through it checks for all docx extension documents it and adds it to an array list.
IndexTheDocument Method
This method creates a new Index instance and runs its generateIndeces method.




TextCopier Class
This class requires a XWPFParagraph instance as well as 2 XWPFDocument instances one with the document text needs to be copied to and one that contains the text that needs to be copied from.
copyText method
This method creates a new paragraph in the Document instance that needs to receive the copy then it creates a list of that document’s paragraphs followed by using the setParagraph method from XWPFDocument class from apache POI. 
newFileforConcatenation Class
This constructor just takes a String of a path where a new word document needs to be created the path should end with the name for the new word document example “mypath\newdocument.docx” The constructor makes a new File instance then writes it to the computer it makes a new XWPFDocument instance which is then written to the new file. Each method is simply there to get the things created in the constructor and therefore are omitted as their names are exactly what they do.
pictureCopier Class
This class serves the purpose of copying over a paragraph with pictures in it to a document that needs to have that paragraph copied into it. In order to be constructed it requires a XWPFParagraph 2 XWPFDocument instances and a path of the folder where the document that’s receiving the data resides. It has one public method and one private method. 
copyAllImages Method
This is the public method that does the work for the class. This method loops over all of the runs in the paragraph with the information that needs to be copied.  Each run is checked for having a picture and text. If the first run has no picture its text is copied into a new paragraph in the document that is receiving the data. If the run has pictures a loop is begun to iterate over all of the pictures in said run the pictures width and height values are then determined and the appentImagetoDoc method in the same class is run.  Next still within the loop any extra text following a picture is added to the paragraph. The method ends with exception checks.
appendImagetoDoc Method
This method adds a picture to the document that needs to receive it. Its parameters are a picture which needs to be appended, a XWPFrun that contains any text that might surround the picture, the ParagraphAlignment of the paragraph in which the original picture was in, a Boolean to check if the picture is its own paragraph as well as the pictures width and height. The method checks the Boolean based on that it either appends. This method sets alignment copies text and adds the picture all using apache POI methods from the XWPFParagraph class and the XWPFRun class.
TableCopierClass
This is the current class used for copying tables. This class cannot handle tables with images. This class is also limited to specific types of tables those being tables that do not have visible cell lines. For tables with cell lines there is a folder of classes capable of computing such operations including tables with pictures in them. However those classes are currently unused and require further testing. This class is constructed with an XWPFTable and two instances of XWPFDocument. 
copyTargetTable
The public method for this class it creates a new table in the document that needs the table to be copied into it then runs the private method copyTable.
copyTable Method
Takes two XWPFTable Instances named source and target this method then uses the XML beans library in tandem with ApachePOI to copy all the information from source to target.
copyParagraph
Takes two XWPFParagraph instance source and target, copies source to target this method is only used for copying paragraphs within tables.


DefinitionWeights Class
This class serves as a way pairing user defined strings and their respective groups to a weight that is needed for sorting. Its constructor is just two strings one of the definition, one of what the definition the group belongs to and an integer which is the weight that was assigned to the group. All of its methods simply return these values.
Index Class
This class performs the actions of indexing two paragraphs together within the document. It requires a String containing the path of the document that needs to have two of its paragraphs linked together as well as an instance of the IndexValues class, documentation of this class can be found later in the document.
generateIndeces Method
This method calls the private method MapLinksAndBookmars which does the actual work of linking two paragraphs together.
createHyperLinkRunToAnchor Method
Takes a XWPFParagraph instance and a String and creates a hyperlink to the string which should be the name of a bookmark in the document
createBookMarkedparagraph Method
This method takes a XWPFParagraph instance a String to name the bookmark and an integer which is the bookmarks id. It then creates a new bookmark in the document gives it a name and attaches it to the paragraph that was passed through the parameter.
Test Method
Takes two paragraphs source and target, bookmarks target and creates a hyperlink on source to target with createBookMarkedparagraph and createHyperLinkRunToAnchor.
Methods.
Clear Method
Clears all the extra text from a paragraph with a hyperlink so there is no repeated text.
MapLinksAndBookmarks
Takes a path of a document that needs to be indexed and an IndexValues instance using the instances list of requirements. This works by opening the document with the given path in the parameter then iterating over all of the paragraphs and finding the first and second instances of the paragraphs with the requirements gotten from IndexValues the method then links the first paragraph to the second. After a paragraph is indexed a new XWPFDocument instance must be initiated with the same path for every value in the IndexValues instance due to restrictions that require that whenever a paragraph is bookmarked and hyperlinked the program must write the results to the File.
IndexValues Class
This class stores a new HashMap and HashSet which contain Strings that help the software identify what needs to be indexed. Hashset to ensure Uniqueness and Hashmap to have a key value pair that can connect the Strings from the Hashset to store paragraphs so they can be linked. Methods in this class are straightforward, they either check to see if a String is in the Hashset or add it to the Hashset as well as the map. This class keeps track of how many different paragraphs have been indexed as well as which paragraphs have already been indexed thanks to the versatility of HashSets.
OrderTheFiles Class
 This class serves the purpose of identifying the user defined orders. It takes a path of folders an array of arrays of strings and the path of document that needs to be ignored. 
Order Method
The purpose of this method is to return arraylist of objects that contain an arraylist of files pertaining to a group given by the user. This object stores the weight of the group that these files belong to, what defines the group, as well as what the name of the group is.
fileTitleAndPath Method
The purpose of this method is to open a microsoft word document extract the title of the document and store the documents title and path into an Object named TitleAndPath. The title of a document is determined by the second paragraph within the document, it is important to note the reason for the implementation of this determination was due to the requirements of the project. The algorithm to determine which paragraph in a document is easily changeable and can even be set based on user input if need be, this however would require a new algorithm.
Definitions Method
This method is what extracts the definitions given by the user in the original input of the software. Due to each group definition requirement needing to be in pairs of 2 this method takes an array list of array lists and extracts only the indices that pertain to an even number as that is how the definitions are inputted through the GUI.
groupings Method
This method does the same thing as the Definitions method however it only extracts the name of the groups by picking the odd indices from the user input.
inTheMap Method
Although this is a helper method and ideally should not need to be explored extensively it is important to note that this method serves the purpose of assigning a grouping and weight to documents that don't match any user input, this is what allows the program to function despite no user input.
setWeight Method
This method sets the weight of a given definition based on the order the user declared it in the input.
ParagraphIndexLinker Class
This is a helper class designed to hold two paragraphs that need to be linked together.


TitleandPath Class
This is a helper class that is designed to hold a documents path as well as its respective title.


WriteTitlesToFiles Class
This class serves the purpose of writing the Titles of the files to a new word document. The input is an ArrayList GroupingForSort objects that contain the user defined groups, group names, and their weight.
Write Method
This method does the actual work of writing to the word document. It takes the file path given in the constructor and creates a new instance of a XWPFDocument using that path. Afterwards  the method iterates over the ArrayList given in the constructor, it then checks what group each set of files belong to, writes the name of the group preceding the titles and iterates through the ArrayList of TitleandPaths to write the titles in proper order to the document. Note if a set of titles does not belong to a specific group no group name is written preceding these titles.
GroupingForSort Class
This class is designed to hold an arraylist of grouped files, their definitions and their group names, and the group’s weight. It requires an ArrayList in the construction, a String that serves as a definition for the grouping and a HashMap of the Definitions.
StaticMethods Class
This class just holds two static methods, one to change the orientation of a document or portion of a document between landscape and portrait, and the other is a boolean method that checks whether a document is in landscape or portrait mode. Note the rest of the StaticClasses folder is either useless or minor classes containing static methods so they have been left out of this document.
Omitted Classes:
CleanUpDoc, CreateFooter, UserDefinedOrder, CopyCellWithAPicture, CopyTableParagraphTextOnly, CopyTables, TableContentCopier, TableShellCreatorCopy.
These classes were either superfluous or unused in the current iteration of software.