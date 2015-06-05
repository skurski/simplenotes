package notes.panel;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

abstract public class NotePanel extends JPanel {
	abstract public JMenuBar addMenu();
	
	Map<String, JButton> createButtons(Map<String, JButton> buttonMap, 
											String[] names, String[] objName) {
		for(int i=0; i<names.length; i++)
			buttonMap.put(objName[i], new JButton(names[i]));
		
		return buttonMap;
	}
	
	abstract public Map<String, JButton> getButtons();
}
