package notes.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import notes.calculator.Calculator;

public class IntroPanel extends NotePanel {
	private Map<String, JButton> _buttonMap = new HashMap<String, JButton>();
	private Map<String, JPanel> _panelMap = new HashMap<String, JPanel>();

	public IntroPanel() {		
		/************************ CREATE BUTTONS **************************************/
		String[] names = {"Kontakty telefoniczne","Edytor tekstowy", "Kalkulator"};
		String[] objName = {"contactBtn","notesBtn", "calcBtn"};
		createButtons(_buttonMap,names,objName);
		GridLayout grid = new GridLayout(3,1);
		grid.setVgap(20);
        _panelMap.put("buttPanel",  new JPanel(grid));

		_panelMap.get("buttPanel").add(_buttonMap.get("contactBtn"));
		_panelMap.get("buttPanel").add(_buttonMap.get("notesBtn"));
		_panelMap.get("buttPanel").add(_buttonMap.get("calcBtn"));
        _panelMap.get("buttPanel").setPreferredSize(new Dimension(400,160));
        
		actionButton();
		/********************** END OF CREATE BUTTONS **********************************/
		
		/************************ CREATE TEXT ***************************************/
		_panelMap.put("label", new JPanel(new BorderLayout()));
		_panelMap.get("label").add(new JLabel("<html><body><div>Podręczny zestaw biurowy."
				+ "</div><div>Autor: Piotr Skurski</div></body></html>"));
        _panelMap.get("label").setPreferredSize(new Dimension(400,100));
		/*********************** END OF CREATE TEXT **********************************/

		createGUI();
	}
	
	private void actionButton() {
		_buttonMap.get("calcBtn").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        SwingUtilities.invokeLater(new Runnable() {
		            public void run() {
		            	new Calculator();
		            }
		        });
			}
		});
	}
	
	private void createGUI() {
		add(_panelMap.get("label"), BorderLayout.PAGE_START);
		add(_panelMap.get("buttPanel"), BorderLayout.CENTER);		
		setPreferredSize(new Dimension(600,200));
	}
	
	public Map<String, JButton> getButtons() {
		return _buttonMap;
	}
	
	public JMenuBar addMenu() {
		return null;
	}
}
