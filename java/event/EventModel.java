import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class EventModel extends Model {

	final String HDR = "EventModel::";
	
	private PropertyChangeSupport pcs;	

	public EventModel() {
		super();

		this.pcs = new PropertyChangeSupport(this); 

		System.out.println(HDR + "ctor()::");		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) { 

		final String DEBUG = HDR + "addPropertyChangeListener()::";

		pcs.addPropertyChangeListener(listener);

		System.out.println(DEBUG);
	}	
	
	@Override
	public void fireEvent(String property, Object value) {

		final String DEBUG = HDR + "fireEvent()::";

		pcs.firePropertyChange(property, null, value);		

		System.out.println( DEBUG + "Event Model Booster !! ::" + value.toString() );
	}	
}
