package notes.panel;

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
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;

import notes.model.CsvTableModel;
import notes.model.Person;

public class ContactPanel extends NotePanel {
	private Map<String, JPanel> _panelMap = new HashMap<String, JPanel>();
	private CsvTableModel _tableModel = new CsvTableModel();
	private Map<String, JButton> _buttonMap = new HashMap<String, JButton>();
	private Map<String, JMenuItem> _itemMap = new HashMap<String, JMenuItem>();
	private JTable _table = new JTable(_tableModel);
	private JTextField[] _textField = new JTextField[5];
	private JMenuBar menuBar = new JMenuBar();
	private final static int extraWindowWidth = 50;

	public ContactPanel() {
		/********************* ADD MENU BAR *********************************************/
		setMenu(); 
		actionMenu();
		/********************* END OF ADD MENU BAR **************************************/
		
		/***************** CREATE SCROLLPANE WITH SORTABLE TABLE ************************/
		_panelMap.put("contactPanel", new JPanel(new BorderLayout()));
		JScrollPane scrollPane = new JScrollPane();
		_table = new JTable(_tableModel);
		
        //set column width and center the text
		int[] colWidth = {30,50,10,50};
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<_table.getColumnCount(); i++) {
        	_table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        	if(i == _table.getColumnCount() -1)
        		_table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        	else
        		_table.getColumnModel().getColumn(i).setPreferredWidth(colWidth[i]);
        }
		
		scrollPane.setViewportView(_table);	
		_table.setPreferredScrollableViewportSize(new Dimension(680,250));
		_table.setRowSorter(new TableRowSorter(_tableModel));
		_panelMap.get("contactPanel").add(scrollPane);
		/***************** END OFCREATE SCROLLPANE WITH SORTABLE TABLE ******************/

		/********************** CREATE FORM PANEL **************************************/
		_panelMap.put("formPanel", createFormPanel(new String[] 
				{"Imie ", "Nazwisko ", "Wiek ", "Email ", "Telefon "}));
		/******************** END OF CREATE FORM PANEL *********************************/
		
		/************************ CREATE BUTTONS **************************************/
		String[] names = {"Dodaj rekord","Usuń rekord", "Wyczyść formularz"};
		String[] objName = {"addButt","delButt", "clearButt"};
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
                int returnVal = chooser.showSaveDialog(_panelMap.get("contactPanel"));
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
                int returnVal = chooser.showOpenDialog(_panelMap.get("contactPanel"));
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
			panel.add(_textField[i] = new JTextField(30));
		}
		return panel;
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
        
        _buttonMap.get("clearButt").addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		for(JTextField field: _textField)
        			field.setText("");
        	}
        });
	}
	
	private void createGUI() {
		add(_panelMap.get("contactPanel"), BorderLayout.PAGE_START);
		add(_panelMap.get("formPanel"), BorderLayout.CENTER);
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

