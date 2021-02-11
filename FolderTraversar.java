import java.io.File;

public class FolderTraversar {
	private String indent = "";
	private File originalFileObject;
	private File fileObject;
public static void main(String args[])
{
	FolderTraversar fts=new FolderTraversar(new File(args[0]));
	fts.traverse();
}
	public FolderTraversar(File fileObject) {
		this.originalFileObject = fileObject;
		this.fileObject = fileObject;
	}

	public void traverse() {
		recursiveTraversal(fileObject);
	}

	public void recursiveTraversal(File fileObject) {
		if (fileObject.isDirectory()) {
			indent = getIndent(fileObject);
			System.out.println(indent + fileObject.getName());
			File allFiles[] = fileObject.listFiles();
			for (File aFile : allFiles) {
				recursiveTraversal(aFile);
			}
		} else if (fileObject.isFile()) {
			System.out.println(indent +" "+ fileObject.getName());
		}
	}

	private String getIndent(File fileObject) {
		String original = originalFileObject.getAbsolutePath();
		String fileStr = fileObject.getAbsolutePath();
		String subString = fileStr.substring(original.length(),
				fileStr.length());
		String indent = " ";
		for (int index = 0; index<subString.length(); index++) {
			char aChar = subString.charAt(index);
			if (aChar == File.separatorChar) {
				indent = indent +" ";
			}
		}
		return indent;
	}
}