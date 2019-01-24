package racingview;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import model.RacingSimulatorObserver;

public abstract class GenericRacingTable extends JPanel implements RacingSimulatorObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	protected MyGenericRacingTableModel tableModel;
	protected CupChooserPanel _cupChooserPanel;
	protected CharacterChooserPanel _characterChooserPanel;
	protected JPanel _selectedCupImage;
	protected JPanel _selectedCupPanel;
	
	protected JTable _table;
	
	abstract class MyGenericRacingTableModel extends AbstractTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		protected String[] header;
		
		@Override
		public int getColumnCount() {
			return header.length;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			return header[columnIndex];
		}
		
		void refresh() {
			fireTableStructureChanged();
		}
		
		@Override
		public int getRowCount() {
			return 0;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return null;
		}
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		@Override
		public Class getColumnClass(int column)
        {
			return getValueAt(0, column).getClass();
        }
	}

	@Override
	public void registered(CharacterChooserPanel characterChooserPanel, ImagesPanel imagesPanel, CupChooserPanel cupChooserPanel, JPanel selectedCupImage, JPanel selectedCupPanel) {
		_selectedCupPanel = selectedCupPanel;
		_selectedCupImage = selectedCupImage;
		_characterChooserPanel = characterChooserPanel;
		_cupChooserPanel = cupChooserPanel;
		tableModel.refresh();
	}

	@Override
	public void cupSelected() {
		tableModel.refresh();
		_selectedCupImage.removeAll();
		ImageIcon icon = new ImageIcon(this.getClass().getResource("/images/" + _cupChooserPanel.getSelectedCup() + "trophy" + ".jpg"));
		icon.setImage(icon.getImage().getScaledInstance(390, 230, 1));
		_selectedCupImage.add(new JLabel(icon));
		_selectedCupImage.updateUI();
		_selectedCupPanel.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createLineBorder(Color.BLACK),  _cupChooserPanel.getSelectedCup() + " Cup"));
	}

	@Override
	public void characterAdded() {
		tableModel.refresh();
	}

	@Override
	public void reset() {
		tableModel.refresh();
	}
	
	public GenericRacingTable() {
		initGUI();
	}
	
	protected void initGUI() {
		this.setLayout(new BorderLayout());
		_table = new JTable(tableModel);
		_table.setShowGrid(false);
		JScrollPane jscroll = new JScrollPane(_table);
		jscroll.getViewport().setBackground(Color.WHITE);
		this.add(jscroll, BorderLayout.CENTER);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		_table.setDefaultRenderer(String.class, centerRenderer);
	}
}