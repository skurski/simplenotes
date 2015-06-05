package notes.calculator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

public class Calculator extends JFrame {
	private Font myFont = new Font("Tahoma", Font.PLAIN, 22);
	private Map<String, JButton> functionButtons = new HashMap<String, JButton>();
	JPanel buttonPanel = null;
	JPanel textPanel = null;
	JPanel rightPanel = null;
	final JButton[] buttons = new JButton[16];
	final JTextField text = new JTextField();
	Mathematic math = new Mathematic();
	
	public Calculator() {
		buttonPanel = new JPanel(new GridLayout(4,4));
		textPanel = new JPanel(new GridLayout(1,5));
		rightPanel = new JPanel(new GridLayout(4,1));
		
		//buttonPanel	
		String[] labels = {"7","8","9","*","4","5","6","/","1","2","3","-","0","00",".","+"};
		for(int i=0; i<16; i++) {
			buttons[i] = new JButton(labels[i]);
			buttons[i].setFont(myFont);
			buttonPanel.add(buttons[i]);
		}
		buttonPanel.setBorder(new MatteBorder(4,4,4,4,Color.white));
		
		//function Buttons - right panel
		String[] buttLabels = {"DEL", "AC", "<html>&radic;</html>", "="};
		String[] keys = {"delete", "reset", "radic", "calc"};
		for(int i=0; i<4; i++) {
			functionButtons.put(keys[i], new JButton(buttLabels[i]));
			rightPanel.add(functionButtons.get(keys[i]));
		}
		rightPanel.setBorder(new MatteBorder(4,4,4,4,Color.white));	
		
		//textPanel
		text.setHorizontalAlignment(JTextField.RIGHT);
		text.setEditable(false);
		text.setBackground(Color.white);
		text.setFont(myFont);
		textPanel.add(text);
		textPanel.setBorder(new MatteBorder(10,10,10,10,Color.white));
		
		createAndShowGUI();
	}
	
	void setActions() {
		final String operCond1 = "+-*/.";
		final String operCond2 = "+*/.";
		for(int i=0; i<buttons.length; i++) {
			final int j = i;
			buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(text.getText().length() > 0) {
						Character beforeChar = text.getText().charAt(text.getText().length()-1);
						if(operCond1.contains(beforeChar.toString()) && operCond1.contains(buttons[j].getText()))
							text.setText(text.getText());
						else
							text.setText(text.getText() + buttons[j].getText());
					} else if(operCond2.contains(buttons[j].getText())) {
						text.setText("");
					} else {
						text.setText(text.getText() + buttons[j].getText());
					}
				}
			});
		}
		
		functionButtons.get("reset").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				text.setText("");
			}
		});
		
		functionButtons.get("calc").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double res = calculate(text.getText());
				if(res == null) text.setText(text.getText());
				else text.setText(res.toString());
			}
		});
		
		functionButtons.get("radic").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Double res = radic(text.getText());
				if(res == null) text.setText(text.getText());
				else text.setText(res.toString());
			}
		});
		
		functionButtons.get("delete").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res = deleteChar(text.getText());
				text.setText(res);
			}
		});
	}
	
	private Double calculate(String operation) {
		return math.run(operation);
	}
	
	private Double radic(String operation) {
		return math.radic(operation);
	}
	
	private String deleteChar(String operation) {
		if(operation.length() > 0)
			return operation.substring(0, operation.length()-1);
		else
			return "";
	}
	
	void createAndShowGUI() {
		setActions();
		add(textPanel, BorderLayout.PAGE_START);
		add(buttonPanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.LINE_END);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new Calculator();
            }
        });

	}
}
