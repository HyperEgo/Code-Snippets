class ClientSubscriber:
    """Client subscriber for event object(s)"""

    def __init__(self, publisher):
        """Initialize client members vars and catch event method(s)"""
        self.pub = publisher
        self.pub.event_handler.link(self.catchEvent, "TIMESYNC_PROPERTY")
        print("ClientSubscriber.__init__()")

    def catchEvent(self, sync):
        print("ClientSubscriber.catchEvent()")
        print("ClientSubscriber: caught obj = "+f'{sync}')
        print(sync.getInfo())

