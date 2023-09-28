import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Model implements IModel {

	final String HDR = "Model::";
	
	private PropertyChangeSupport pcs;

	public Model() {

		this.pcs = new PropertyChangeSupport(this); 

		System.out.println(HDR + "ctor()::");
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) { 

		final String DEBUG = HDR + "addPropertyChangeListener()::";

		pcs.addPropertyChangeListener(listener);

		System.out.println(DEBUG);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {

		final String DEBUG = HDR + "removePropertyChangeListener()::";

		pcs.removePropertyChangeListener(listener);

		System.out.println(DEBUG);
	}
	
	public void fireEvent(String property, Object value) {

		final String DEBUG = HDR + "fireEvent()::";

		pcs.firePropertyChange(property, null, value);		

		System.out.println( DEBUG + value.toString() );
	}	

}
