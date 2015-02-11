package songlib;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class TextInputView extends JPanel implements ITextInput {

	public JTextField nameInput, artistInput, albumInput, yearInput;

	public JLabel nameLabel, artistLabel, albumLabel, yearLabel;

	public JButton cancel, save;
	
	public static boolean ADDING_NEW_SONG = true;
	
	public BufferedImage back_arrow, check_mark;

	public ISongLibrary songLibraryInterface;
	
	public ISongList songListInterface; 
	
	
	public TextInputView(final ISongLibrary songLibraryInterface, final ISongList songListInterface) {

		super(new GridLayout(0, 2));

		this.songLibraryInterface = songLibraryInterface;
		this.songListInterface = songListInterface;
		
		try {
			
			back_arrow = ImageIO.read(new File("src/back_arrow.png"));
			check_mark = ImageIO.read(new File("src/check_mark.png"));
			
		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
		}
		
		nameInput = new JTextField("");
		nameLabel = new JLabel("Name: ", JLabel.CENTER);
		nameLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
		nameLabel.setLabelFor(nameInput);

		artistInput = new JTextField("");
		artistLabel = new JLabel("Artist: ", JLabel.CENTER);
		artistLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
		artistLabel.setLabelFor(artistInput);

		albumInput = new JTextField("");
		albumLabel = new JLabel("Album: ", JLabel.CENTER);	
		albumLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
		albumLabel.setLabelFor(albumInput);
		

		yearInput = new JTextField("");
		yearLabel = new JLabel("Released: ", JLabel.CENTER);
		yearLabel.setFont(new Font("Courier New", Font.PLAIN, 16));
		yearLabel.setLabelFor(yearInput);

		add(nameLabel);
		add(nameInput);
		add(artistLabel);
		add(artistInput);
		add(albumLabel);
		add(albumInput);
		add(yearLabel);
		add(yearInput);
		
	
		cancel = new JButton();
		save = new JButton();
		
		cancel.setIcon(new ImageIcon(back_arrow));
		cancel.setFocusPainted(false);
		save.setIcon(new ImageIcon(check_mark));
		save.setFocusPainted(false);
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				restoreInputColor();
				songLibraryInterface.showPane("AEDButtons");
			}
		});
		
		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String name = nameInput.getText().trim();
				String artist = artistInput.getText().trim();
				String album = albumInput.getText().trim();
				String year = yearInput.getText().trim();
				
				if (name.length() == 0 || artist.length() == 0) {
					notifyBadInput();
					return;
				}
				
				Song new_song = new Song(name, artist, album, year);
				
				if (songListInterface.saveSong(new_song) > 0) {
					songLibraryInterface.showPane("AEDButtons");
					restoreInputColor();
					repaint();
					clearTextFields();
					return;
				}
				
				notifyBadInput();
				
				
			}
		});
		
		
		add(cancel);
		add(save);

	}
	
	public void notifyBadInput() {
		
		nameInput.setForeground(new Color(255, 0, 0));
		artistInput.setForeground(new Color(255, 0, 0));
		
	}
	
	public void restoreInputColor() {
		nameInput.setForeground(new Color(0, 0, 0));
		artistInput.setForeground(new Color(0, 0, 0));
	}
	
	public void populateFields(int index) {
	
		nameInput.setText(songListInterface.getSong(index).getName());
		artistInput.setText(songListInterface.getSong(index).getArtist());
		albumInput.setText(songListInterface.getSong(index).getAlbum());
		yearInput.setText(songListInterface.getSong(index).getYear());
	}
	
	public void clearTextFields() {
	
		nameInput.setText("");
		artistInput.setText("");
		albumInput.setText("");
		yearInput.setText("");
	}


}
