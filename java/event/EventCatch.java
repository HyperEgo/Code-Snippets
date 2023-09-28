import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

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
			case IModel.PRACTICE_EVENT :
				String info = (String)evt.getNewValue();
				System.out.println(DEBUG + info );
				break;
			case IModel.PAYLOAD_EVENT :
				Payload load = (Payload)evt.getNewValue();
				System.out.println(DEBUG + load.name() + "::" + load.getRadius() );				
				break;
		}
	}	

    /**
     * Main()
     * @param args String[] input args
     */
    public static void main(String[] args) {

		Model parent = new Model();

		new EventCatch(parent);
		new EventFire(parent).fire();
		new EventFire(parent).firePayload(Payload.BALLISTIC);
		new EventFire(parent).firePayload(Payload.BIO_CHEMICAL);

		/**
		 * Event model inheritor - child
		 */
		Model child = new EventModel();

		new EventCatch(child);
		new EventFire(child).firePayload(Payload.NUCLEAR);
		new EventFire(child).fire();
    }	
}
