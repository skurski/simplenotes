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

public class IntroPanel extends AbstractPanel {
	private Map<String, JButton> _buttonMap = new HashMap<String, JButton>();
	private Map<String, JPanel> _panelMap = new HashMap<String, JPanel>();

	public IntroPanel() {		
		/************************ CREATE BUTTONS **************************************/
		String[] names = {"Kontakty telefoniczne","Edytor tekstowy", "Kalkulator", "O programie", "Zamknij"};
		String[] objName = {"contactBtn","noteBtn", "calcBtn", "aboutBtn", "closeBtn"};
		createButtons(_buttonMap,names,objName);
		GridLayout grid = new GridLayout(objName.length,1);
		grid.setVgap(20);
        _panelMap.put("buttPanel",  new JPanel(grid));

		_panelMap.get("buttPanel").add(_buttonMap.get("contactBtn"));
		_panelMap.get("buttPanel").add(_buttonMap.get("noteBtn"));
		_panelMap.get("buttPanel").add(_buttonMap.get("calcBtn"));
		_panelMap.get("buttPanel").add(_buttonMap.get("aboutBtn"));
		_panelMap.get("buttPanel").add(_buttonMap.get("closeBtn"));
        _panelMap.get("buttPanel").setPreferredSize(new Dimension(460,300));
        
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
		_buttonMap.get("closeBtn").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		        System.exit(0);
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
