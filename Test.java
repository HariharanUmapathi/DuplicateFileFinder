//test java
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Test{
private static int fileCount=0;
	public static void main(String...args)throws IOException{
FileReader fr;
BufferedReader br;
try{
//This Block Finds the files.txt is exists if does not exists it will terminate the program with a error message;
fr=new FileReader(new File("files.txt"));
br =new BufferedReader(fr);
while(br.readLine()!=null){
fileCount++;
}

}
catch(FileNotFoundException e){
	System.out.println("Error:File Not Found");
	System.exit(0);
}
System.out.println("Scanning input Path");
System.out.println("Totally Scanned Files:" +fileCount);
System.out.println("Scanning Complete");
}
}