package songlib;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SongInfoView extends JPanel implements ISongInfo {

	public JLabel name, artist, album, year;
	public JLabel nameLabel, artistLabel, albumLabel, yearLabel;



	public SongInfoView() {

		super(new GridLayout(0 , 2));
		setBorder(BorderFactory.createLineBorder(new Color(20,40,60)));
		
		name = new JLabel();
		artist = new JLabel();
		album = new JLabel();
		year = new JLabel();
		
		nameLabel = new JLabel("Name: ", JLabel.CENTER);
		nameLabel.setFont(new Font("Courier", Font.PLAIN, 16));
		nameLabel.setLabelFor(name);
		artistLabel = new JLabel("Artist: ", JLabel.CENTER);
		artistLabel.setFont(new Font("Courier", Font.PLAIN, 16));
		artistLabel.setLabelFor(artist);
		albumLabel = new JLabel("Album: ", JLabel.CENTER);
		albumLabel.setFont(new Font("Courier", Font.PLAIN, 16));
		albumLabel.setLabelFor(album);
		yearLabel = new JLabel("Released: ", JLabel.CENTER);
		yearLabel.setFont(new Font("Courier", Font.PLAIN, 16));
		yearLabel.setLabelFor(year);
		
		add(nameLabel);
		add(name);
		add(artistLabel);
		add(artist);
		add(albumLabel);
		add(album);
		add(yearLabel);
		add(year);
		
	}

	public void setInfoText(Song song) {
		String name = song.getName();
		String album = song.getAlbum();
		String artist = song.getArtist();
		String year = song.getYear();
		this.name.setText(name);
		this.artist.setText(artist);
		this.album.setText(album);
		this.year.setText(year);
		repaint();
	}
	
	public void clearText() {
		name.setText("");
		artist.setText("");
		album.setText("");
		year.setText("");
	}
	
	
}

