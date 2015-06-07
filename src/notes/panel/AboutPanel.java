package notes.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import notes.model.NoteModel;

public class AboutPanel extends AbstractPanel {
	private Map<String, JPanel> _panelMap = new HashMap<String, JPanel>();
	private JTextArea _textArea = new JTextArea(28,60);
	private NoteModel _noteModel = new NoteModel();
	private final String _aboutFile = "about.txt";

	public AboutPanel() {
    	_textArea.setText(_noteModel.getData(_aboutFile).toString());
    	_textArea.setEditable(false);
    	_textArea.setWrapStyleWord(true);
    	_textArea.setLineWrap(true);
    	_textArea.setBackground(Color.GRAY);
    	_textArea.setForeground(Color.WHITE);

		
		/***************** CREATE SCROLLPANE WITH TEXT PANEL ************************/
		_panelMap.put("aboutPanel", new JPanel(new BorderLayout()));
		JScrollPane scrollPane = new JScrollPane();
		_panelMap.put("textPanel", new JPanel(new BorderLayout()));
		_panelMap.get("textPanel").add(_textArea);	
		scrollPane.setViewportView(_panelMap.get("textPanel"));	
		_panelMap.get("aboutPanel").add(scrollPane);
		/***************** END OFCREATE SCROLLPANE WITH TEXT PANEL ******************/

		createGUI();
	}
	
	private void createGUI() {
		add(_panelMap.get("aboutPanel"), BorderLayout.CENTER);
        setPreferredSize(new Dimension(600, 400));
        setLocation(150, 150);
		setVisible(false);
	}
	
    public JMenuBar addMenu() {
    	return null;
    }
    
	public Map<String, JButton> getButtons() {
		return null;
	}
}

