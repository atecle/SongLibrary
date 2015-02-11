package app;

import helpers.Loader;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import songlib.ISongList;
import songlib.Song;
import songlib.SongLibraryView;

public class SongLib {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run() {

				File file = new File("library.log");

				ArrayList<Song> songs = new ArrayList<Song>(20);
				Loader loader = null;

				if (file.exists()) {
					try {
						loader = new Loader(file);
					} catch (IOException e1) {
						System.err.println("IOException instantiating Loader " + e1.getMessage());
					}
					songs = loader.songs;
				} else {
					try {
						file.createNewFile();
					} catch (IOException e2) {
						System.err.println("Error creating new file " + e2.getMessage());
					}

				}

				SongLibraryView window = new SongLibraryView(songs);
				final ISongList songListInterface = window.songListInterface;

				//Shutdown Hook
				window.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						try {
							Loader loader = new Loader(new File("library.log"));
							loader.writeFile(songListInterface.getSongs());
						} catch (IOException er) {
							er.printStackTrace();
						}
					}
				});

				window.setVisible(true);
				window.setLocationRelativeTo(null);
			}
			//COMMENT
		});

	}

}
