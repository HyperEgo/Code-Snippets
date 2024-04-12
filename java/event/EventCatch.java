import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Event catcher, impl PropertyChangeListner
 */
public class EventCatch implements PropertyChangeListener {

	final String HDR = "EventCatch::";

	Model model;
	
	public EventCatch(Model md) { 

		this.model = md;

		model.addPropertyChangeListener(this);

		System.out.println(HDR + "ctor()::");		
	}

	public void propertyChange(PropertyChangeEvent evt) {

		final String DEBUG = HDR + "propertyChange()::";

		switch( evt.getPropertyName() ) {
			case Model.PRACTICE_EVENT :
				String info = (String)evt.getNewValue();
				System.out.println(DEBUG + info );
				break;
			case Model.PAYLOAD_EVENT :
				Payload load = (Payload)evt.getNewValue();
				System.out.println(DEBUG + load.name() + "::" + load.getRadius() );				
				break;
		}
	}	
}
