package view;

public class EventsQueueTable extends GenericTable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unchecked")
	class MyEventsQueueTableModel extends MyGenericTableModel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
		public MyEventsQueueTableModel() {
			header = new String[]{ "#", "Time", "Type" };
		}
	
		@Override
		public int getRowCount() {
			return events == null ? 0 : events.size();
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			String v = null;
			switch (columnIndex) {
				case 0:
					v = "" + events.get(rowIndex).getPositionIndex();
					break;
				case 1:
					v = "" + events.get(rowIndex).getScheduledTime();
					break;
				case 2:
					v = events.get(rowIndex).toString();
					break;
				default:
					break;
			}
			return v;
		}
		
		public void refresh() {
			fireTableStructureChanged();
		}
	}
	
	public EventsQueueTable() {
		initGUI();
	}
	
	protected void initGUI() {
		tableModel = new MyEventsQueueTableModel();
		super.initGUI();
	}
}