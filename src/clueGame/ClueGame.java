//Authors: Nathan Lambert, Elliott McCabe

package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.Color;

public class ClueGame extends JFrame{
	
	private Board board;
	JPanel panel;
	private static JFrame frame;
	
	public ClueGame() {
		// Board is singleton, get the only instance
		Board board;
		board = Board.getInstance();
		// set the file names to use our own files
		board.setConfigFiles("data/BoardLayout.csv", "data/ClueRooms.txt", "data/CluePlayers.txt", "data/ClueWeapons.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
		
		panel = board;
		add(panel, BorderLayout.CENTER);
		
		JPanel controlGui = new ControlGUI();
		add(controlGui, BorderLayout.SOUTH);
		
		//add the menu bar
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
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
	
	public static void main(String[] args) {
		ClueGame game = new ClueGame();
		frame = game;
		frame.setSize(700, 850);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		
		game.setVisible(true);
		
		
	}
	
	
}
