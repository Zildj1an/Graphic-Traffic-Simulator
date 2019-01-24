package racingview;

import javax.swing.ImageIcon;

public class SelectedCharactersTable extends GenericRacingTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	class MySelectedCharactersTableModel extends MyGenericRacingTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MySelectedCharactersTableModel() {
			header = new String[]{ "Name", "Speed", "Luck", "Icon" };
		}
		
		@Override
		public int getRowCount() {
			return _characterChooserPanel.getSelectedCharacters().size();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String v = null;
			switch (columnIndex) {
				case 0:
					 v = _characterChooserPanel.getSelectedCharacters().get(rowIndex);
					 break;
				case 1:
					 v = _characterChooserPanel.getSpeedMap().get(_characterChooserPanel.getSelectedCharacters().get(rowIndex)).toString();
					 break;
				case 2:				
					v = _characterChooserPanel.getLuckMap().get(_characterChooserPanel.getSelectedCharacters().get(rowIndex)).toString();
					 break;
				case 3:
					ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + _characterChooserPanel.getSelectedCharacters().get(rowIndex) + ".png"));
					icon.setImage(icon.getImage().getScaledInstance(42, 42, 1));
					return icon;
				default:
					break;
			}
			return v;
		}
	}

	@Override
	protected void initGUI() {
		tableModel = new MySelectedCharactersTableModel();
		super.initGUI();
		_table.setRowHeight(42);
	}
}