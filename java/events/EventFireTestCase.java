package ibcs.ms.imsdcm.distributiveCollab;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

public class EventFireTestCase extends TestCase {
	
	private final PropertyChangeSupport pcs;
	
	public EventFireTestCase() {
		pcs = new PropertyChangeSupport(this);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
		System.out.println("******************* EventFireTestCase::addPropertyChangeListener()");
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
		System.out.println("******************* EventFireTestCase::removePropertyChangeListener()");
	}
	
	public void fireEvent() {
		final String TEST_VALUE = "TEST_VALUE_DUMMY";
		pcs.firePropertyChange(EventUtil.TEST_CASE_PROPERTY, null, TEST_VALUE);
		System.out.println("******************* EventFireTestCase::fireEvent() FIRE! value = "+TEST_VALUE);
	}
	
	@BeforeClass
	public static void setup() throws Exception { }
	
	@Test
	public void test() throws Exception {
		Assert.assertNotNull(pcs);
	}
	
	@AfterClass
	public static void teardown() { }

}  // EventFireTestCase