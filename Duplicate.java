import java.util.ArrayList;
import java.util.HashSet;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.Collections;
//Exceptions
import java.io.FileNotFoundException;
import java.io.IOException;

public class Duplicate {
	private static ArrayList<File> filedata = new ArrayList<File>();
	private static HashSet<String> namelist = new HashSet<String>();

	public static void main(String args[]) throws IOException {
		Process child;
		
		System.out.println("Enter The Path to find Duplicates..:");
		Scanner sc=new Scanner(System.in);
		String Path=sc.nextLine();
		System.out.println("Path: "+Path); 
		try{
		child=Runtime.getRuntime().exec("cmd /c dir "+Path+" /s /b >files.txt");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		FileInputStream fin;
		BufferedReader br = null;
		Ui gui = new Ui();
		if (args.length == 0)
			try {
				fin = new FileInputStream("files.txt");
			} catch (FileNotFoundException e2) {
				System.out.println("Filelist is not given");
			}
		else {
			File file = new File(args[0]);
			if (file.isDirectory()) {
				File list[] = file.listFiles();
				for (File x : list)
					System.out.println(x);
				System.out.println(list.length);
			} else if (file.isFile()) {
				String line = br.readLine();
				int maxlen = 0;
				while (line != null) {
					File a = new File(line);
					if (a.isFile())
						filedata.add(a);
					namelist.add(a.getName());
System.out.println(String.format("%-100s Size: %s",a.getName(),unit(a.length())));
					line = br.readLine();
				}
				if (filedata.size() != 0) {
					System.out.println("Loaded Files:" + filedata.size() + "Unique FileNames " + namelist.size());
					int dup = 0;
					long dupsize = 0;
					String origin = "";
					String destin = "";

					ArrayList<String> list = new ArrayList<String>();
					int size = filedata.size() - 1;
					for (int i = 0; i < filedata.size() - 1; i++) {
						gui.setProgress(Math.round((float) i / size * 100));
						for (int j = i + 1; j < filedata.size(); j++)
							if (filedata.get(i).getName().equals(filedata.get(j).getName())) {
								if (filedata.get(i).length() == filedata.get(i).length()) {
									origin = filedata.get(i).getPath();
									destin = filedata.get(j).getPath();
									list.add(origin + " " + destin);
									dupsize += filedata.get(j).length();
									gui.update(dup, unit(dupsize), "File dul");
									dup++;
								}
							}

					}
					gui.setData(0, 0, "Serial NO");
					gui.setData(0, 1, "Origin");
					gui.setData(0, 2, "Destination");

					for (int i = 0; i < list.size(); i++) {
						gui.setData(i + 1, 0, (i + 1) + "");
						gui.setData(i + 1, 1, origin);
						gui.setData(i + 1, 2, destin);
					}
					System.out.println("Duplicates: " + dup + "  Storage using:" + unit(dupsize));
					gui.setProgress(100);
				} else
					System.out.println("files.txt found empty \nCreate a file with dir /s /b /a:-d /o:n");

			}

		}
	}

	public static String unit(long length) {
		int unitchoice = 0;
		String result = "";
		double size = (double) length;
		String unit[] = { " B", "KB", "MB", "GB" };
		while ((double) size > (double) 1024) {
			size = size / 1024;
			unitchoice++;
		}
		return String.format("%8.2f %s", size, unit[unitchoice]);
	}

}

class Ui extends JFrame {
	private JPanel mainpane;
	private JLabel progressLabel;
	private JProgressBar pBar;
	private JLabel dup;
	private JLabel bytes;
	private JTable table;
	private JLabel current;
	private JScrollPane jsp;
	private JButton open;
	private JPanel errormsg;

	Ui() {
		this.errormsg = new JPanel();
		errormsg.add(new JLabel("Some error occured"));
		this.mainpane = new JPanel();
		this.progressLabel = new JLabel();
		this.pBar = new JProgressBar();
		this.pBar.setValue(10);
		this.dup = new JLabel("Duplicate Files:");
		this.bytes = new JLabel("Size Occupied");
		this.open = new JButton("Open");
		open.setBounds(0, 140, 100, 50);
		String title[] = { "Serial No", "Original File ", "Duplicate File" };
		String content[][] = new String[65532][10];
		this.table = new JTable(content, title);
		this.current = new JLabel("Current file");
		this.jsp = new JScrollPane(table);
		setLayout(null);
		mainpane.setBounds(0, 0, 720, 600);
		errormsg.setBounds(0, 0, 80, 80);
		progressLabel.setBounds(0, 20, 100, 30);
		setTitle("Duplicate Finding By Filename");
		pBar.setBounds(0, 40, 100, 40);
		dup.setBounds(0, 80, 100, 20);
		bytes.setBounds(0, 100, 100, 20);
		table.setBounds(0, 120, 200, 150);
		jsp.setBounds(0, 120, 300, 150);
		current.setBounds(0, 130, 300, 40);
		progressLabel.setText("Progress :");
		mainpane.add(progressLabel);
		mainpane.add(pBar);
		mainpane.add(dup);
		mainpane.add(bytes);
		mainpane.add(current);
		mainpane.add(jsp);
		mainpane.add(errormsg);
		add(mainpane);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(720, 1080);
		setVisible(true);
	}

	public JPanel getMain() {
		return this.mainpane;
	}

	public void errorHandler(int errorCode) {

		switch (errorCode) {
		case 0:
			System.out.println("No Error");
			this.getMain().setVisible(true);
			break;
		case 1:
			System.out.println("Error Code:404");
		default:
			System.exit(0);
		}
	}

	public void setData(int x, int y, String data) {
		this.table.setValueAt(data, x, y);
	}

	public void setBg(int value) {
		if (value >= 0 && value <= 30)
			this.pBar.setForeground(Color.RED);
		else if (value > 31 && value <= 60)
			this.pBar.setForeground(Color.BLUE);
		else {
			this.pBar.setForeground(Color.GREEN);
		}
	}

	public void update(int dup, String size, String file) {
		this.dup.setText("Files Count :" + dup);
		this.bytes.setText("Space Occupied:  " + size);
		this.current.setText("File " + file);
	}

	public void setProgress(int progress) {
		this.setBg(progress);
		this.progressLabel.setText("Progress :" + String.format("%3s", progress) + "%");
		this.pBar.setValue(progress);
	}
}