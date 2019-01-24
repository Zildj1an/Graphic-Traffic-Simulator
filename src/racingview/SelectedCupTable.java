package racingview;

public class SelectedCupTable extends GenericRacingTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		
	class MySelectedCupTableModel extends MyGenericRacingTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MySelectedCupTableModel() {
			header = new String[]{ "Race", "Name" };
		}
		
		@Override
		public int getRowCount() {
			return _cupChooserPanel.getCupsMap().get(_cupChooserPanel.getSelectedCup()).length;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String v = null;
			switch (columnIndex) {
				case 0:
					 v = "#" + (rowIndex + 1);
					 break;
				case 1:
					 v = _cupChooserPanel.getCupsMap().get(_cupChooserPanel.getSelectedCup())[rowIndex];
					 break;
				default:
					break;
			}
			return v;
		}
	}

	@Override
	protected void initGUI() {
		tableModel = new MySelectedCupTableModel();
		super.initGUI();
	}
}