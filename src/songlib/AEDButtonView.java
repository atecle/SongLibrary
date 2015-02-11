package songlib;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class AEDButtonView extends JPanel {

	private JButton edit, delete, add;
	
	private BufferedImage pencil, trash, plus;
	final ISongLibrary songLibraryInterface;
	final ISongList songListInterface;
	final ITextInput textInputInterface;

	public AEDButtonView(final ISongLibrary songLibraryInterface, final ISongList songListInterface, final ITextInput textInputInterface) {
		
		super(new FlowLayout(FlowLayout.LEFT));

		this.songLibraryInterface = songLibraryInterface;
		this.songListInterface = songListInterface;
		this.textInputInterface = textInputInterface;
		
		
		try {
			pencil = ImageIO.read(new File("src/pencil.png"));
			trash = ImageIO.read(new File("src/trash.png"));
			plus = ImageIO.read(new File("src/plus.png"));
		} catch (IOException e) {
			System.out.println("IO error " + e.getMessage());
		}


		edit = new JButton(new ImageIcon(pencil));
		delete = new JButton(new ImageIcon(trash));
		add = new JButton(new ImageIcon(plus));
		
		//Prevent from being highlighted by default
		edit.setFocusPainted(false);
		add.setFocusPainted(false);
		delete.setFocusPainted(false);

		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (songListInterface.listSize() == 0 || songListInterface.selectedIndex() < 0) return;
	
				TextInputView.ADDING_NEW_SONG = false;
				songLibraryInterface.showPane("TextInput");
				textInputInterface.populateFields(songListInterface.selectedIndex());
				
			}
		});
		
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			
				
				TextInputView.ADDING_NEW_SONG = true;
				textInputInterface.clearTextFields();
				songLibraryInterface.showPane("TextInput");

			}
		});
		
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (songListInterface.listSize() == 0 || songListInterface.selectedIndex() < 0) return;
				
 				songListInterface.removeSong();
			}
		});
		
		add(add);
		add(edit);
		add(delete);


	}

}
