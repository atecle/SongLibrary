package songlib;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SongLibraryView extends JFrame implements ISongLibrary {

	public SongListView songlist;
	
	public SongInfoView songInfo;
	
	public AEDButtonView aedview;
	
	public TextInputView textview;
	
	public JPanel infoPane;
	
	public JPanel inputPane;
	
	public CardLayout card_layout;
	
	public ISongList songListInterface;
	
	public SongLibraryView(ArrayList<Song> songs) {
		
		super("Song Library");
		setLayout(new BorderLayout());

		songInfo = new SongInfoView();
		songlist = new SongListView(songs, songInfo, new TextInputView(this, songlist));
		textview = new TextInputView(this, songlist);
		aedview = new AEDButtonView(this, songlist, textview);
		
		this.songListInterface = songlist;
		
		
		//A JPanel for scrollable song list
		JPanel listPane = new JPanel();				
		listPane.setLayout(new BorderLayout());
		listPane.setBorder(BorderFactory.createEtchedBorder());
		
		/*
		 * A JPanel for everything else, directly beside song list. Song info goes in one JPanel WITHIN this one, and Add/Edit/Delete Buttons goes in another JPanel
		 * also WITHIN this one, directly below the Song Info JPanel. Using GridLayout to have SongInfo panel right above the add/edit/delete panel
		 */
		infoPane = new JPanel();
		infoPane.setLayout(new GridLayout(0, 1));
		infoPane.setBorder(BorderFactory.createLineBorder(new Color(100, 200, 50)));
			
		infoPane.add(songInfo);
	
		inputPane = new JPanel();
		card_layout = new CardLayout();
		inputPane.setLayout(card_layout);
		infoPane.add(inputPane);
		
		inputPane.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 200)));
		inputPane.add(aedview, "AEDButtons");
		inputPane.add(textview, "TextInput");
		card_layout.show(inputPane, "AEDButtons");
		
		listPane.add(songlist, BorderLayout.EAST);
		
		add(listPane, BorderLayout.EAST);
		add(infoPane, BorderLayout.CENTER); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setSize(750, 300);
		setMinimumSize(new Dimension(400, 400));
	}


	//pass "AEDButtons" or "TextInput" to display aedview or textview respectively
	@Override
	public void showPane(String name) {
		card_layout.show(inputPane, name);
		repaint();
	}
	
}
