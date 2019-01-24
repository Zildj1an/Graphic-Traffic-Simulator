package racingview;

import javax.swing.ImageIcon;

import model.Kart;
import view.GenericTable;

public class ClassificationTable extends GenericTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	class MyClassificationTableModel extends MyGenericTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyClassificationTableModel() {
			header = new String[]{ "#", "Name", "Speed", "Lap", "Icon"};
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
					 v = "" + (rowIndex + 1);
					 break;
				case 1:
					 v = map.getVehicles().get(rowIndex).getId();
					 break;
				case 2:
					 v = "" + map.getVehicles().get(rowIndex).getSpeed();
					 break;
				case 3:
					 v = "" + ((Kart) map.getVehicles().get(rowIndex)).getLap();
					 break;
				case 4:
					 ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + map.getVehicles().get(rowIndex).getId() + ".png"));
					 icon.setImage(icon.getImage().getScaledInstance(90, 90, 1));
					 return icon;
				default:
					break;
			}
			return v;
		}
		
		@SuppressWarnings({ "rawtypes" })
		@Override
		public Class getColumnClass(int column)
        {
			return getValueAt(0, column).getClass();
        }
	}

	@Override
	protected void initGUI() {
		tableModel = new MyClassificationTableModel();
		super.initGUI();
		table.setRowHeight(90);
	}
}