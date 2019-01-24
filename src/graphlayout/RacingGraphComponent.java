package graphlayout;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

public class RacingGraphComponent extends GraphComponent implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Thread hilo;
	private final int DELAY=5;
	protected Map<String, Integer> _x;
	protected Map<String, Integer> _y;
	protected Map<String, Integer> _x_past;
	protected Map<String, Integer> _y_past;
	protected Map<String, Integer> _x_current;
	protected Map<String, Integer> _y_current;
	
	public RacingGraphComponent() {
		super();
		
		setDoubleBuffered(true);
		 empezado = false;
		 avanzado = false;
		 _x = new HashMap<String, Integer>();
		 _y = new HashMap<String, Integer>();
		 _x_past = new HashMap<String, Integer>();
		 _y_past = new HashMap<String, Integer>();
		 _x_current = new HashMap<String, Integer>();
		 _y_current = new HashMap<String, Integer>();
	}
	
	@Override
	protected void drawVehicle(Graphics g, double x, double y, int x1, int y1, int diam, int xDir, int yDir, String txt) {
		Image img = new ImageIcon(getClass().getResource("/images/" + txt + ".gif")).getImage();
		if(!empezado) {
			_x_past.put(txt, x1 + xDir * ((int) x) - diam / 2);
			_y_past.put(txt, y1 + yDir * ((int) y) - diam / 2);
			_x_current.put(txt, _x_past.get(txt));
			_y_current.put(txt, _y_past.get(txt));
		}
		else {
			if(avanzado) {
				_x_past.put(txt, _x.get(txt));
				_y_past.put(txt, _y.get(txt));
				_x_current.put(txt, _x_past.get(txt));
				_y_current.put(txt, _y_past.get(txt));
			}
		}
		_x.put(txt, x1 + xDir * ((int) x) - diam / 2);
		_y.put(txt, y1 + yDir * ((int) y) - diam / 2);
		int positionX = _x_current.get(txt);
		int positionY = _y_current.get(txt);
		g.drawImage(img, positionX, positionY, diam, diam, this);
	}
	
	@Override
	public void run() {
		while(true){
			ciclo();
	    	repaint();
	        try {
	        	Thread.sleep(DELAY);
	        }catch(InterruptedException err){
	            System.out.println(err);
	        }
	    }
	}
	
	public void ciclo(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 	for (String key : _x.keySet()) {
		        int viejox = _x_past.get(key);
		        int viejoy = _y_past.get(key);
		        int nuevox = _x.get(key);
		        int nuevoy = _y.get(key);
		        
		        int nextPositionX = _x_current.get(key) + ((nuevox - viejox) / 5);
		        int nextPositionY = _y_current.get(key) + ((nuevoy - viejoy) / 5);
		        	if((viejox < nuevox && nextPositionX >= nuevox) || 
			        		(viejox > nuevox && nextPositionX <= nuevox) ||
			        		(viejoy < nuevoy && nextPositionY >= nuevoy) ||
			        		(viejoy > nuevoy && nextPositionY <= nuevoy) ||
			        		(nextPositionX < viejox && nuevox > viejox) ||
			        		(nextPositionX > viejox && nuevox < viejox) ||
			        		(nextPositionY < viejoy && nuevoy > viejoy) ||
			        		(nextPositionY > viejoy && nuevoy < viejoy)) {
			        	_x_current.put(key, viejox);
			        	_y_current.put(key, viejoy);
			        }
			        else {
			        	_x_current.put(key, nextPositionX);
			        	_y_current.put(key, nextPositionY);
			        }
		    }
    }
	
	@Override
    public void addNotify(){ 
	    super.addNotify();
	    hilo = new Thread(this);
	    hilo.start();
    }
	
	@Override
	public void reset() {
		empezado = false;
		 avanzado = false;
		 _x = new HashMap<String, Integer>();
		 _y = new HashMap<String, Integer>();
		 _x_past = new HashMap<String, Integer>();
		 _y_past = new HashMap<String, Integer>();
		 _x_current = new HashMap<String, Integer>();
		 _y_current = new HashMap<String, Integer>();
	}
}
