package notes.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import notes.model.NoteModel;

public class NotePanel extends AbstractPanel {
	private Map<String, JPanel> _panelMap = new HashMap<String, JPanel>();
	private Map<String, JButton> _buttonMap = new HashMap<String, JButton>();
	private Map<String, JMenuItem> _itemMap = new HashMap<String, JMenuItem>();
	private JTextArea _textArea = new JTextArea(24,60);
	private JMenuBar menuBar = new JMenuBar();
	private NoteModel noteModel = new NoteModel();
	private String _fileName = null;
	private final static int extraWindowWidth = 50;

	public NotePanel() {
		/********************* ADD MENU BAR *********************************************/
		setMenu(); 
		actionMenu();
		/********************* END OF ADD MENU BAR **************************************/
		
		/***************** CREATE SCROLLPANE WITH TEXT PANEL ************************/
		_panelMap.put("notePanel", new JPanel(new BorderLayout()));
		JScrollPane scrollPane = new JScrollPane();
		_panelMap.put("textPanel", new JPanel(new BorderLayout()));
		_panelMap.get("textPanel").add(_textArea);
		
		scrollPane.setViewportView(_panelMap.get("textPanel"));	
		_panelMap.get("notePanel").add(scrollPane);
		/***************** END OFCREATE SCROLLPANE WITH TEXT PANEL ******************/

		
		/************************ CREATE BUTTONS **************************************/
		String[] names = {"Wyczyść formularz"};
		String[] objName = {"clearButt"};
		createButtons(_buttonMap,names,objName);
        _panelMap.put("buttPanel",  new JPanel() {
            //Make the panel wider than it really needs, so
            //the window's wide enough for the tabs to stay
            //in one row.
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
        });
        
        _panelMap.get("buttPanel").setPreferredSize(new Dimension(500,160));
        for(Map.Entry<String, JButton> entry : _buttonMap.entrySet()) 
    		_panelMap.get("buttPanel").add(entry.getValue());

		actionButton();
		/********************** END OF CREATE BUTTONS **********************************/

		createGUI();
	}
	
    private void setMenu() {
        String[] menuLabel = {"Edycja"};
        String[][] itemLabel = {{"Zapisz", "Zapisz jako", "Wczytaj z pliku", "Zamknij"}};
        String[][] itemObjName = {{"saveItem", "saveAsItem", "readItem", "closeItem"}};
        createMenu(menuLabel,itemLabel,itemObjName);  
    }

	private JMenuBar createMenu(String[] menuLabel, String[][] itemLabel, String[][] itemObjName) {
		JMenu[] menu = new JMenu[menuLabel.length];
		for(int i=0; i<menuLabel.length; i++) {
			menu[i] = new JMenu(menuLabel[i]);
			menuBar.add(menu[i]);
		}
		
		JMenuItem item = null;
		for(int i=0; i<itemLabel.length;i++) {
			for(int j=0; j<itemLabel[i].length; j++) {
				item = new JMenuItem(itemLabel[i][j]);
				menu[i].add(item);
				_itemMap.put(itemObjName[i][j], item);
			}
		}	
		return menuBar;
	}
    
	private void actionMenu() {
		_itemMap.get("saveItem").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				noteModel.saveData(_fileName, _textArea.getText());
			}
		});
		_itemMap.get("closeItem").addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
        _itemMap.get("saveAsItem").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Txt", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(_panelMap.get("notePanel"));
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                	noteModel.saveData(chooser.getSelectedFile().getName(), _textArea.getText());
                }
            }
        });
        _itemMap.get("readItem").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Txt", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(_panelMap.get("notePanel"));
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                	_fileName = chooser.getSelectedFile().getName();
                	_textArea.setText(noteModel.getData(_fileName).toString());
                }
            }
        });
	}
	
	private void actionButton() {    
        _buttonMap.get("clearButt").addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		_textArea.setText("");
        	}
        });
	}
	
	private void createGUI() {
		add(_panelMap.get("notePanel"), BorderLayout.CENTER);
		add(_panelMap.get("buttPanel"), BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(600, 400));
        setLocation(150, 150);
		setVisible(false);
	}
	
    public JMenuBar addMenu() {
    	return menuBar;
    }
    
	public Map<String, JButton> getButtons() {
		return null;
	}
}

