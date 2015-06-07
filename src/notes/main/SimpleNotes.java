package notes.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import notes.panel.AboutPanel;
import notes.panel.ContactPanel;
import notes.panel.IntroPanel;
import notes.panel.AbstractPanel;
import notes.panel.NotePanel;

class SimpleNotes extends JFrame {
	private Map<String, JButton> _buttonMap = new HashMap<String, JButton>();
	private Map<String, AbstractPanel> _mainPanels = new HashMap<String, AbstractPanel>();

	SimpleNotes() {
		/********************* ADD PANELS AND BUTTONS *********************************************/
		addPanelsAndButtons();
		/********************* END OF ADD PANELS AND BUTTONS **************************************/
		
		/********************* ADD BUTTONS ACTION *********************************************/
		addActionToButtons();
		/********************* END OF ADD BUTTONS ACTION **************************************/
	}
	
	private void addPanelsAndButtons() {
		_mainPanels.put("introPanel", new IntroPanel());
		_mainPanels.put("contactPanel", new ContactPanel());
		_mainPanels.put("notePanel", new NotePanel());
		_mainPanels.put("aboutPanel", new AboutPanel());
		
		_buttonMap.put("returnBtn", new JButton("Powrót"));	
		_buttonMap.putAll(_mainPanels.get("introPanel").getButtons());
	}
	
	private void addActionToButtons() {
		_buttonMap.get("contactBtn").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mainPanels.get("introPanel").setVisible(false);
				addContactPanel();
			}
		});
		
		_buttonMap.get("noteBtn").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mainPanels.get("introPanel").setVisible(false);
				addNotePanel();
			}
		});
		
		_buttonMap.get("aboutBtn").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_mainPanels.get("introPanel").setVisible(false);
				addAboutPanel();
			}
		});
		
		_buttonMap.get("returnBtn").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (Map.Entry<String, AbstractPanel> entry : _mainPanels.entrySet()) {
				    entry.getValue().setVisible(false);
				}
				SimpleNotes.this.setJMenuBar(null);
				checkReturnBtnVisibility();
				addIntroPanel();
			}
		});
	}
	
	void addContactPanel() {
		add(_mainPanels.get("contactPanel"), BorderLayout.CENTER);
		this.setJMenuBar(_mainPanels.get("contactPanel").addMenu());
		_mainPanels.get("contactPanel").setVisible(true);	
		_buttonMap.get("returnBtn").setVisible(true);
	}
	
	void addNotePanel() {
		add(_mainPanels.get("notePanel"), BorderLayout.CENTER);
		this.setJMenuBar(_mainPanels.get("notePanel").addMenu());
		_mainPanels.get("notePanel").setVisible(true);	
		_buttonMap.get("returnBtn").setVisible(true);
	}
	
	void addAboutPanel() {
		add(_mainPanels.get("aboutPanel"), BorderLayout.CENTER);
		this.setJMenuBar(null);
		_mainPanels.get("aboutPanel").setVisible(true);	
		_buttonMap.get("returnBtn").setVisible(true);
	}
	
	void addIntroPanel() {
		add(_mainPanels.get("introPanel"), BorderLayout.CENTER);
		_mainPanels.get("introPanel").setVisible(true);	
		_buttonMap.get("returnBtn").setVisible(false);
	}
	
	void addReturnButton() {
		JPanel returnPanel = new JPanel();
		returnPanel.add(_buttonMap.get("returnBtn"));
		add(returnPanel, BorderLayout.PAGE_END);
	}
	
	void checkReturnBtnVisibility() {
		if(_mainPanels.get("introPanel").isVisible() == true) 
			_buttonMap.get("returnBtn").setVisible(false);
		else 
			_buttonMap.get("returnBtn").setVisible(true);
	}
	
	void createAndShowGUI() {
		addIntroPanel();
		addReturnButton();
		checkReturnBtnVisibility();

        setPreferredSize(new Dimension(700,500));
        setLocation(150, 150);
        setTitle("Zestaw biurowy: kontakty, notatki i kalkulator");
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	SimpleNotes app = new SimpleNotes();
                app.createAndShowGUI();
            }
        });
	}
}

