package view;

public class VehiclesTable extends GenericTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	class MyVehiclesTableModel extends MyGenericTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyVehiclesTableModel() {
			header = new String[]{ "ID", "Road", "Location", "Speed", "Km", "Faulty Units", "Itinerary" };
		}
		
		@Override
		public int getRowCount() {
			return map == null ? 0 : map.getVehicles().size();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String v = null;
			switch (columnIndex) {
				case 0:
					 v = map.getVehicles().get(rowIndex).getId();
					 break;
				case 1:
					if(map.getVehicles().get(rowIndex).atDestination())
						v = "arrived";
					else
						v = map.getVehicles().get(rowIndex).getRoad().getId();
					 break;
				case 2:
					if(map.getVehicles().get(rowIndex).atDestination())
						v = "arrived";
					else
						v = "" + map.getVehicles().get(rowIndex).getLocation();
					 break;
				case 3:
					 v = "" + map.getVehicles().get(rowIndex).getSpeed();
					 break;
				case 4:
					 v = "" + map.getVehicles().get(rowIndex).getKilometrage();
					 break;
				case 5:
					 v = "" + map.getVehicles().get(rowIndex).getFaultyTime();
					 break;
				case 6:
					 v = map.getVehicles().get(rowIndex).getItineraryString();
					 break;
				default:
					break;
			}
			return v;
		}
	}

	@Override
	protected void initGUI() {
		tableModel = new MyVehiclesTableModel();
		super.initGUI();
	}
}