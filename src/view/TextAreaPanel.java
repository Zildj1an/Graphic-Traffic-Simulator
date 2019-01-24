package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

/*Los componentes del GUI que contengan texto heredarán de él*/

public abstract class TextAreaPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected JTextArea textArea;	
	
	public TextAreaPanel(String title, boolean editable){
		this.setLayout( new BorderLayout() );
		textArea = new JTextArea(40,30);
		textArea.setEditable(editable);
		this.add(new JScrollPane(textArea));
		this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), title));
		textArea.setFont(new Font("Sheriff", Font.ITALIC, 17));
	}
	
	public String getText() {
		return textArea.getText();
	}
	
	public void setText(String text) {
		textArea.setText(text);
	}
	
	public void clear() {
		textArea.setText("");
	}
	
	public void insert(String text) {
		textArea.insert(text, textArea.getCaretPosition());
	}
}
