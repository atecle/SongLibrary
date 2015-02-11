package helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import songlib.Song;

public class Loader {

	public ArrayList<Song> songs;

	public Loader(File file) throws IOException {
		parseFile(file);
	}

	private void parseFile(File file) throws IOException {
		BufferedReader br;


		br = new BufferedReader(new FileReader(file));


		String line;
		ArrayList<Song> list = new ArrayList<Song>(25); 
		while ((line = br.readLine()) != null) {		
			String[] tokens = line.split("\\|", -1);
			Song song = new Song(tokens[0].trim(), tokens[1].trim(), tokens[2].trim(), tokens[3].trim());
			list.add(song);
		}

		br.close();

		this.songs = list;
	}

	public void writeFile(ArrayList<Song> songs) throws IOException {
		File out_file = new File("library.log");

		FileWriter fo = new FileWriter(out_file, false);

		for (int i = 0; i < songs.size(); i++) {
			Song song = songs.get(i);
			fo.write(song.getName()+"|"+song.getArtist()+"|"+song.getAlbum()+"|"+song.getYear()+"\n");
		}
		fo.close();
	}
}
