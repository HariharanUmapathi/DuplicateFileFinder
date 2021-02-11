import java.io.File;
import java.util.ArrayList;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.IOException;

class FileManager {
	private static ArrayList<File> fileList;
	public static ArrayList<File> foldList;
	private String root;
	private int filesCount;
	private int folderCount;
	private String filter;

	public static void main(String... args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		FileManager flg=new FileManager();
			try {flg = new FileManager(args[0]);}
			catch(NullPointerException e)
			{
			System.out.println("Invalid Path")	;
			}
//Listing Folders
		flg.getFolders(flg.root);
//Collections.sort(fileList);
		int option=0;
		while(option<7)
		{
		System.out.println(" " + String.format("Files Count : %-30s  Folder Count : %s", flg.filesCount, flg.folderCount));
		flg.menu();
		try{
		option=Integer.parseInt(br.readLine());
		}
		catch(NumberFormatException E)
		{
		System.out.println("Error On User Input only Number keys accepted");	
		}
		catch(IOException e)
		{
		System.out.println("Error Occured");
		System.exit(0);	
		}
		switch(option)
		{
		case 1://show file list
				flg.printList(flg.getFileList());
				break;
		case 2://show folder list
				flg.printList(flg.getFoldList());
				break;
		case 6:System.exit(0);
		default:
			System.out.println("To Exit Press 6");
		}
		}
		
//flg.printList();
//flg.filter("wav");
//flg.printList(flg.filter());
//flg.bulkDelete(flg.filter());
//		flg.emptyDelete(flg.foldList);
	}
	public ArrayList<File> getFoldList()
	{
		return this.foldList;
	}
	public ArrayList<File> getFileList() {
		return this.fileList;
	}
	public void menu()
	{
	System.out.println("Menu");	
	System.out.println("1.Show Files List");	
	System.out.println("2.Show Folder List");	
	System.out.println("3.Find Duplicates By Name");	
	System.out.println("4.Find Duplicates By Size");	
	System.out.println("5.Sort :name=1,size=2,lastModified=3");	
	System.out.println("6.exit");	
	}
	public void emptyDelete(ArrayList<File> list) {
		int dir = 0, emDir = 0;
		for (File x : list) {
			System.out.print(x.getName() + "\n");
			if (x.isDirectory()) {
				dir++;
				if (x.listFiles().length == 0) {
					emDir++;
					if (x.delete())
						System.out.println(x.getPath() + "is Deleted");
				}
			}
		}
		System.out.println("Directories : " + dir + " EmptyDirectories : " + emDir);
	}

	public void bulkDelete(ArrayList<File> list) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Are you sure to delete" + list.size() + " items filtered by filter");
		char option = sc.nextLine().toCharArray()[0];
		System.out.println(option);
		if (option == 'n' || option == 'N')
			System.out.println(list.size() + "is not Deleted, Deleting Operation cancelled");
		else if (option == 'Y' || option == 'y')
			for (File x : list) {
				if (x.delete())
					System.out.println(x.getName() + " is deleted");
			}
	}

	public ArrayList<File> filter() {
		int count = 0;
		ArrayList<File> result = new ArrayList<File>();
		BufferedReader br;
		String type = "";
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			type = br.readLine();
		} catch (IOException E) {

		}
		for (File item : this.fileList) {
			if (item.getName().contains(type) || item.getPath().contains(type)) {
				System.out.println(item.getName() + " " + item.getPath());
				count++;
				result.add(item);
			}
		}
		System.out.println(count + " Matches in" + FileManager.fileList.size());
		return result;
	}

	public void filter(String type) {
		int count = 0;
		for (File item : this.fileList) {
			if (item.getName().contains(type)) {
				System.out.println(item.getName() + " " + item.getPath());
				count++;
			}
		}
		System.out.println(count + " Matches in" + FileManager.fileList.size());
	}

	public void printList(ArrayList<File> list) {
		for (File x : list) {
			System.out.println("" + String.format("%-50s %s", x.getName(), x.getAbsolutePath()));
		}
	}

	public void printList() {
		for (File x : this.fileList) {
			System.out.println("" + String.format("%-50s %s", x.getName(), x.getAbsolutePath()));
		}
	}

	public void getFolders(String path) {
		File fObj = new File(path);
		File[] fObjAr = fObj.listFiles();
		for (File x : fObjAr) {
			if (x.isDirectory()) {
				this.getFolders(x.getAbsolutePath());
				this.foldList.add(x);
				this.folderCount += 1;
			} else {
				this.fileList.add(x);

			}
			this.filesCount = this.fileList.size();
		}
	}

	public FileManager(String filePath) {
		this.root = filePath;
		this.fileList = new ArrayList<File>();
		this.foldList = new ArrayList<File>();
		this.filesCount = 0;
		this.folderCount = 0;
	}
	public FileManager()
	{
		this.fileList = new ArrayList<File>();
		this.foldList = new ArrayList<File>();
		this.filesCount = 0;
		this.folderCount = 0;
	}

	public FileManager(String filePath, String fileExt) {
		this.fileList = new ArrayList<File>();
		this.filesCount = 0;
		this.folderCount = 0;
		this.root = filePath;
		this.filter = fileExt;
		System.out.println("Filter :" + this.filter);
	}
}
