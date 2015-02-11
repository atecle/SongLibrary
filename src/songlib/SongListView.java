package songlib;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class SongListView extends JPanel implements ListSelectionListener, ISongList {

	public JList<String> list;

	public JScrollPane scrollPane;

	public DefaultListModel<String> listModel;

	public ArrayList<Song> songs;
	
	private HashMap<String, Song> song_map;

	public ISongInfo songInfoInterface;

	public ITextInput textInputInterface;

	
	public SongListView(ArrayList<Song> songs, ISongInfo songInfoInterface, ITextInput textInputInterface) {

		super(new BorderLayout());
		setBorder(BorderFactory.createEtchedBorder());

		song_map = new HashMap<String, Song>(25);
		this.songs = songs;
		this.textInputInterface = textInputInterface;
		this.songInfoInterface = songInfoInterface;
		Collections.sort(this.songs);

		listModel = new DefaultListModel<String>();

		for (int i = 0; i < songs.size(); i++) {
			listModel.add(i, songs.get(i).getName());
			song_map.put(songs.get(i).getName()+songs.get(i).getAlbum(), songs.get(i));
		}

		list = new JList<String>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(this);
		list.setLayoutOrientation(JList.VERTICAL);

		scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(500,560));

		add(scrollPane, BorderLayout.EAST);


	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!e.getValueIsAdjusting()) {
			if (selectedIndex() < 0) {
				songInfoInterface.clearText();
				return;
			}
			Song song = songs.get(selectedIndex());
			
			songInfoInterface.setInfoText(song);
			
		}

	}

	@Override
	public int saveSong(Song song) {


		if (TextInputView.ADDING_NEW_SONG) {
			
			if (song.getName().length() == 0 || song.getArtist().length() == 0) return -1;
			
			String key = song.getName() + song.getArtist();
			
			if (song_map.containsKey(key)) return -1;
			
			song_map.put(key, song);
		
			int index = Collections.binarySearch(songs, song);
			if (index < 0) {
				index++;
				index*=-1;
			}
			songs.add(index, song);
			listModel.add(index, song.getName());
			list.setSelectedIndex(index);
			return 1;
		}
	
		int editing_index = selectedIndex();
		Song temp = songs.get(editing_index);
		String key = songs.get(editing_index).getName() + songs.get(editing_index).getArtist();
		song_map.remove(key);
		listModel.removeElementAt(editing_index);
		songs.remove(editing_index);
		
		if (song.getName().length() == 0 || song.getArtist().length() == 0
				|| song_map.containsKey(song.getName()+song.getArtist())) {
			song_map.put(key, temp);
			int readd = Collections.binarySearch(songs, temp);
			readd++;
			readd*=-1;
			listModel.add(readd, temp.getName());
			songs.add(readd, temp);
			return -1;
		}
		
		song_map.put(song.getName()+song.getArtist(), song);
		int index = Collections.binarySearch(songs, song);
		index++;
		index*=-1;
		listModel.add(index, song.getName());
		songs.add(index, song);
		TextInputView.ADDING_NEW_SONG = false;
		list.setSelectedIndex(index);
		
		return 1;
		
	}


	@Override
	public void removeSong() {
		int index = selectedIndex();
		song_map.remove(songs.get(index).getName()+songs.get(index).getArtist());
		songs.remove(index);
		listModel.removeElementAt(index);
		list.setSelectedIndex(0);
	}
	
	public int listSize() {
		return songs.size();
	}
	
	@Override 
	public int selectedIndex() {
		return list.getSelectedIndex();
	}
	
	public Song getSong(int index) {
		return songs.get(index);
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}
}
