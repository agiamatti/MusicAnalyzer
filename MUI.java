package Music;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class MUI extends JFrame implements ActionListener {

	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	class AnalyzeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			labO.setText(" Output is being analyzed.");

			Scanner sc = null;
			PrintWriter pw1 = null;
			PrintWriter pw2 = null;
			PrintWriter pw3 = null;
			String path = pathName.getText();
			String fileName = "";

			try {
				sc = new Scanner(new FileInputStream(path));
				fileName = path.substring(path.lastIndexOf('/') + 1);
				pw1 = new PrintWriter(new FileOutputStream("/Users/agiamatti/Desktop/Artist_" + fileName));
				pw2 = new PrintWriter(new FileOutputStream("/Users/agiamatti/Desktop/Albums_" + fileName));
				pw3 = new PrintWriter(new FileOutputStream("/Users/agiamatti/Desktop/Misc_" + fileName));
			} catch (FileNotFoundException ioe) {
				labO.setText(" \"" + path + "\" is not a valid input. Please try again.");

			}

			if (pw3 != null) {
				MusicReader.analyzeFile(sc, pw1, pw2, pw3);
				sc.close();
				pw1.close();
				pw2.close();
				pw3.close();
				previewData();
				labO.setText(" Analysis complete. Output is written to desktop. Preview available.");

			}
		}
	}

	private static final long serialVersionUID = -4281166998198483853L;

	// Attributes
	public static final int WIDTH = 1500;
	public static final int HIGHT = 1000;

	private JButton analyzeButton;

	private JLabel lab1;
	private JLabel lab2;
	private JLabel lab3;
	private JLabel lab4;
	private JLabel labO;
	private JLabel labF;
	private JLabel labEmpty = new JLabel("");

	private JTextField pathName;

	private JEditorPane pane1;
	private JEditorPane pane2;
	private JEditorPane pane3;

	// Default Constructor
	public MUI() {
		super(); // Calls the constructor of JFrame

		setSize(WIDTH, HIGHT);
		setTitle("Music Library Analyzer");

		// center window
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);

		// hide is default
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Sets the frame layout to a border layout

		setLayout(new BorderLayout());

		// create the menu
		JMenu prefMenu = new JMenu("Information");

		JMenuItem detailsButton = new JMenuItem("Details");
		detailsButton.addActionListener(this); // Calls the ActionListener
												// defined by this class

		JMenuItem formatButton = new JMenuItem("Format Example");
		formatButton.addActionListener(this); // Calls the ActionListener
												// defined by this class

		prefMenu.add(detailsButton);
		prefMenu.add(formatButton);

		// Finally, add the menu itself to a menu bar then add the menu bar to
		// the frame
		JMenuBar bar1 = new JMenuBar();
		bar1.add(prefMenu);
		this.setJMenuBar(bar1);

		JMenu othersMenu = new JMenu("Others");
		prefMenu.add(othersMenu);

		JMenuItem about = new JMenuItem("About");
		about.addActionListener(this);
		othersMenu.add(about);

		// North Panel
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(5, 1));
		lab1 = new JLabel(
				"\tThis program analyzes a music library data file and outputs analyzed results to the desktop.");
		lab2 = new JLabel("\tInsert the path of the file to be analyzed. In the form of: /Users/****/Desktop/file.txt");

		JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new FlowLayout());

		pathName = new JTextField("", 40);
		pathName.setBackground(Color.WHITE);

		analyzeButton = new JButton("Analyze file");
		analyzeButton.setPreferredSize(new Dimension(200, 25));
		analyzeButton.addActionListener(new AnalyzeListener());

		inputPanel.add(pathName);
		inputPanel.add(analyzeButton);

		northPanel.add(labEmpty);
		northPanel.add(lab1);
		northPanel.add(lab2);
		northPanel.add(labEmpty);
		northPanel.add(inputPanel);

		add(northPanel, BorderLayout.NORTH);

		// West Panel; empty, creates a border for center
		JPanel westPanel = new JPanel();
		add(westPanel, BorderLayout.WEST);

		// East Panel; empty, creates a border for center
		JPanel eastPanel = new JPanel();
		add(eastPanel, BorderLayout.EAST);

		// Center Panel; preview windows

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridLayout(1, 3));
		pane1 = new JEditorPane();
		pane2 = new JEditorPane();
		pane3 = new JEditorPane();

		pane1.setText("Plays by Artists");
		pane2.setText("Plays by Album");
		pane3.setText("Miscellaneous stats");

		pane1.setEditable(false);
		pane2.setEditable(false);
		pane3.setEditable(false);

		JScrollPane sPane1 = new JScrollPane(pane1);
		JScrollPane sPane2 = new JScrollPane(pane2);
		JScrollPane sPane3 = new JScrollPane(pane3);

		centerPanel.add(sPane1);
		centerPanel.add(sPane2);
		centerPanel.add(sPane3);

		add(centerPanel, BorderLayout.CENTER);

		// South Panel; console

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(4, 1));

		lab3 = new JLabel("");
		lab4 = new JLabel(" Console");
		lab4.setFont(new Font("Arial", Font.BOLD, 18));
		labO = new JLabel(" Waiting for input.");
		labF = new JLabel("");
		southPanel.add(lab3);
		southPanel.add(lab4);
		southPanel.add(labO);
		southPanel.add(labF);
		add(southPanel, BorderLayout.SOUTH);
	}

	// We need to define the actionPerformed method
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();

		if (s.equals("About")) {
			JOptionPane.showMessageDialog(null, "Copyright © Alan Giamatti. 2018.", "About", JOptionPane.PLAIN_MESSAGE);
		}

		else if (s.equals("Details")) {
			JOptionPane.showMessageDialog(null,
					"Analyzes a music library xml file. Please insert a path to the xml file in a text format.",
					"Details", JOptionPane.INFORMATION_MESSAGE);
		}

		else if (s.equals("Format Example")) {
			JOptionPane.showMessageDialog(null,
					"Name,, Artists, Album, Artist_Album, Time, Rating, heart, plays, Genre, year, bitrate, date_added, track#, sample rate",
					"Format example", JOptionPane.PLAIN_MESSAGE);
		}

	}

	private void previewData() {

		Scanner sc1 = null;
		Scanner sc2 = null;
		Scanner sc3 = null;

		String fileName = pathName.getText().substring(pathName.getText().lastIndexOf('/') + 1);

		try {

			sc1 = new Scanner(new FileInputStream("/Users/agiamatti/Desktop/Artist_" + fileName));
			sc2 = new Scanner(new FileInputStream("/Users/agiamatti/Desktop/Albums_" + fileName));
			sc3 = new Scanner(new FileInputStream("/Users/agiamatti/Desktop/Misc_" + fileName));
		} catch (FileNotFoundException ioe) {
			labO.setText(" Error with PreviewData.");
		}

		int counter = 0;
		sc1.useDelimiter("\t");
		while (sc1.hasNextLine() && counter < 50) {
			String artist = sc1.next();
			String rest = sc1.nextLine();

			while (artist.length() < 22) {
				artist = artist + " ";
			}

			pane1.setText(pane1.getText() + "\n" + artist + rest);
			counter++;
		}

		counter = 0;
		sc2.useDelimiter("\t");
		while (sc2.hasNextLine() && counter < 50) {
			String album = sc2.next();
			String rest = sc2.nextLine();

			while (album.length() < 50) {
				album = album + " ";
			}

			if (album.length() > 50) {
				album = album.substring(0, 48);
			}

			pane2.setText(pane2.getText() + "\n" + album + rest);
			counter++;
		}

		while (sc3.hasNextLine()) {
			pane3.setText(pane3.getText() + "\n" + sc3.nextLine());
		}

	}

}
