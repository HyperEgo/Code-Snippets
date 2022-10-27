package ibcs.ms.imsdcm.distributiveCollab;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

public class EventCatchTestCase extends TestCase {

	public EventFireTestCase efire;
	
	public EventCatchTestCase() {
		efire = new EventFireTestCase();
	}
	
	@BeforeClass
	public static void setup() throws Exception { }
	
	@Test
	public void test() throws Exeception {
		Assert.assertNotNull(efire);
		efire.addPropertyChangeListener(new EventCatcher());
		efire.fireEvent();
	}
	
	@AfterClass
	public static void teardown() { }
	
	private class EventCatcher implements PropertyChangeListener {
		public void propertyChange(PropertyChangeEvent evt) {
			String propertyName = evt.getPropertyName();
			if ( propertyName.equals(EventUtil.TEST_CASE_PROPERTY) ) {
				final String TEST_VALUE = (String)evt.getNewValue();
				System.out.println("******************* EventCatchtestCase::EventCatcher::propertyChange() CAUGHT! value = "+TEST_VALUE);
			}
			
		}
		
	}  // EventCatcher
	
}  // EventCatchTestCase