package notes.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;

import notes.model.CsvTableModel;
import notes.model.Person;

class CsvTable extends JFrame {
	private Map<String, JPanel> _panelMap = new HashMap<String, JPanel>();
	private CsvTableModel _tableModel = new CsvTableModel();
	private Map<String, JButton> _buttonMap = new HashMap<String, JButton>();
	private Map<String, JMenuItem> _itemMap = new HashMap<String, JMenuItem>();
	private JTable _table = new JTable(_tableModel);
	private JTextField[] _textField = new JTextField[5];
	private final static int extraWindowWidth = 50;

	CsvTable() {
		// Create JMenuBar
		setMenu(); 
		actionMenu();
		
		// Create scrollPane with sortable table
		_panelMap.put("mainPanel", new JPanel(new BorderLayout()));
		JScrollPane scrollPane = new JScrollPane();
		_table = new JTable(_tableModel);
		scrollPane.setViewportView(_table);	
		_table.setPreferredScrollableViewportSize(new Dimension(600,200));
		_table.setRowSorter(new TableRowSorter(_tableModel));
		_panelMap.get("mainPanel").add(scrollPane);
		
		// Create form panel
		_panelMap.put("formPanel", createFormPanel(new String[] 
				{"Imie ", "Nazwisko ", "Wiek ", "Email ", "Telefon "}));
		
		// Create buttons
		String[] names = {"Dodaj rekord","Usuń rekord"};
		String[] objName = {"addButt","delButt"};
		createButtons(names,objName);
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
		_panelMap.get("buttPanel").add(_buttonMap.get("addButt"));
		_panelMap.get("buttPanel").add(_buttonMap.get("delButt"));
		actionButton();
	}
	
    private void setMenu() {
        JMenuBar menuBar = new JMenuBar();
        String[] menuLabel = {"Rekordy", "Notatnik"};
        String[][] itemLabel = {{"Zapisz", "Zapisz jako", "Wczytaj z pliku", "Zamknij"}};
        String[][] itemObjName = {{"saveItem", "saveAsItem", "readItem", "closeItem"}};
        menuBar = createMenu(menuBar,menuLabel,itemLabel,itemObjName);  
        this.setJMenuBar(menuBar);
    }
    
	private JMenuBar createMenu(JMenuBar menuBar, String[] menuLabel, 
		String[][] itemLabel, String[][] itemObjName) {
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
				_tableModel.saveData(null);
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
                    "Csv and Txt", "csv", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showSaveDialog(_panelMap.get("mainPanel"));
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                	_tableModel.saveData(chooser.getSelectedFile().getName());
                }
            }
        });
        _itemMap.get("readItem").addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Csv and Txt", "csv", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(_panelMap.get("mainPanel"));
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                	_tableModel.getData(chooser.getSelectedFile().getName());
                }
            }
        });
	}
	
	private JPanel createFormPanel(String[] names) {
		int len = names.length;
		JPanel panel = new JPanel(new GridLayout(len,len));
		for(int i=0; i<len; i++) {
			panel.add(new JLabel(names[i], SwingConstants.RIGHT));
			panel.add(_textField[i] = new JTextField(10));
		}
		return panel;
	}
	
	private void createButtons(String[] names, String[] objName) {
		for(int i=0; i<names.length; i++)
			_buttonMap.put(objName[i], new JButton(names[i]));
	}
	
	private void actionButton() {
		_buttonMap.get("addButt").addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				Person person = new Person(_textField[0].getText(), _textField[1].getText(),
						_textField[2].getText(), _textField[3].getText(), _textField[4].getText());
				_tableModel.addRow(person);
			}
		});

        _table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(final ListSelectionEvent listEvent) {
                _buttonMap.get("delButt").addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if(!listEvent.getValueIsAdjusting()) {
                            int row = _table.getSelectedRow();
                            _tableModel.removeRow(row);
                        }
                    }
                });
            }
        });
	}
	
	void createAndShowGUI() {
		add(_panelMap.get("mainPanel"), BorderLayout.PAGE_START);
		add(_panelMap.get("formPanel"), BorderLayout.CENTER);
		add(_panelMap.get("buttPanel"), BorderLayout.PAGE_END);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400));
        setLocation(150, 150);
        setTitle("Podręczny notatnik z książką telefoniczną");
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	CsvTable app = new CsvTable();
                app.createAndShowGUI();
            }
        });
	}
}

