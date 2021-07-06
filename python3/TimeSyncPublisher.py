from eventhandler import EventHandler
from TimeSync import TimeSync

class TimeSyncPublisher:
    """Event publisher for TimeSync"""

    def __init__(self):
        """Initialize publisher and event objects"""
        self.event_handler = EventHandler("TIMESYNC_PROPERTY")
        print("TimeSyncPublisher.__init__()")

    def fireEvent(self, sync: TimeSync):
        """Fire event object to subscribers"""
        self.event_handler.fire("TIMESYNC_PROPERTY", sync)
        print("TimeSyncPublisher.fireEvent()")

    def pullTrigger(self):
        """Build TimeSync object internally and fire event"""
        ts = TimeSync()
        self.fireEvent(ts)
        print("TimeSyncPublisher.pullTrigger()")


