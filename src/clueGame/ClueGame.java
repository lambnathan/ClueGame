//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Color;

public class ClueGame extends JFrame{
	private DetectiveNotes notes;
	JPanel panel;
	private static JFrame frame;
	private Board board;
	
	public ClueGame() {
		// Board is singleton, get the only instance
		Board board;
		board = Board.getInstance();
		panel = board;
		// set the file names to use our own files
		board.setConfigFiles("data/BoardLayout.csv", "data/ClueRooms.txt", "data/CluePlayers.txt", "data/ClueWeapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		notes = new DetectiveNotes();

		add(panel, BorderLayout.CENTER);
		
		JPanel controlGui = new ControlGUI();
		JPanel playerCardsGUI = new PlayerCardsGUI(board);
		add(controlGui, BorderLayout.SOUTH);
		add(playerCardsGUI, BorderLayout.EAST);
		
		//add the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createDetectiveNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
	}
	
	private JMenuItem createDetectiveNotesItem() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e)
			{
				notes.setVisible(true);
			}
		}
		item.addActionListener(new MenuItemListener());
		return item;
}
	
	public static void main(String[] args) {
		
		try {
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
			System.err.println("Look and feel not set");
		}
		
		ClueGame game = new ClueGame();
		SplashScreen test = new SplashScreen();
		frame = game;
		frame.setSize(850, 850);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		
		game.setVisible(true);
		test.setVisible(true);
		
	}
	
	
}
