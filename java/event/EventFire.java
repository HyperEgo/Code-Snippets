public class EventFire {

	final String HDR = "EventFire::";

	final String DUD = "DUMMY_HEAD";

	Model model;
	
	public EventFire(Model md) {

		this.model = md;

		System.out.println(HDR + "ctor()::");
	}

	public void fire() {

		final String DEBUG = HDR + "fire()::";

		model.fireEvent(Model.PRACTICE_EVENT, DUD);

		System.out.println(DEBUG);		
	}

	public void firePayload(Payload loadout) {

		final String DEBUG = HDR + "firePayload()::";		

		model.fireEvent(Model.PAYLOAD_EVENT, loadout);

		System.out.println(DEBUG);		
	}	
}
