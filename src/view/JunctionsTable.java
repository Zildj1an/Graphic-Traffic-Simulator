package view;

public class JunctionsTable extends GenericTable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	class MyJunctionsTableModel extends MyGenericTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyJunctionsTableModel() {
			header = new String[]{ "ID", "Green", "Red" };
		}
		
		@Override
		public int getRowCount() {
			return map == null ? 0 : map.getJunctions().size();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String v = null;
			switch ( columnIndex ) {
				case 0:
					 v = map.getJunctions().get(rowIndex).getId();
					 break;
				case 1:
					v = map.getJunctions().get(rowIndex).getGreens();
					 break;
				case 2:
					v = map.getJunctions().get(rowIndex).getReds();
					 break;
				default:
					break;
			}
			return v;
		}
	}
	
	@Override
	protected void initGUI() {
		tableModel = new MyJunctionsTableModel();
		super.initGUI();
	}
}