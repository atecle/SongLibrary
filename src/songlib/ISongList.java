package songlib;

import java.util.ArrayList;

public interface ISongList {

	public int saveSong(Song song);
	
	public void removeSong();
	
	public int listSize();
	
	public int selectedIndex();

	public Song getSong(int index);
	
	public ArrayList<Song> getSongs();
}

