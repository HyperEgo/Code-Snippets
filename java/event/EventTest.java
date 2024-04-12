public class EventTest {

    /**
     * Main
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
