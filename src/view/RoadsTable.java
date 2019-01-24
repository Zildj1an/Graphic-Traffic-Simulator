package view;

public class RoadsTable extends GenericTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	class MyRoadsTableModel extends MyGenericTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyRoadsTableModel() {
			header = new String[]{ "ID", "Source", "Target", "Length", "Max Speed", "Vehicles" };
		}
	
		@Override
		public int getRowCount() {
			return map == null ? 0 : map.getRoads().size();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String v = null;
			switch ( columnIndex ) {
				case 0:
					 v = map.getRoads().get(rowIndex).getId();
					 break;
				case 1:
					 v = map.getRoads().get(rowIndex).getSource().getId();
					 break;
				case 2:
					 v = map.getRoads().get(rowIndex).getDestination().getId();
					 break;
				case 3:
					 v = "" + map.getRoads().get(rowIndex).getLength();
					 break;
				case 4:
					 v = "" + map.getRoads().get(rowIndex).getMaxSpeed();
					 break;
				case 5:
					 v = "" + map.getRoads().get(rowIndex).getVehiclesString();
					 break;
				default:
					break;
			}
			return v;
		}
	}
	
	@Override
	protected void initGUI() {
		tableModel = new MyRoadsTableModel();
		super.initGUI();
	}
}