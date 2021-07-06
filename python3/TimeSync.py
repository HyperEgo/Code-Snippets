class TimeSync:
    """Encapsulate video stream data and functionality"""

    def __init__(self):
        """Initialize video object and external devices"""
        self.infoMsg = "TimeSync msg: Welcome to Streaming & Playback"
        print("TimeSync.__init__()")

    def getInfo(self):
        """Get video stream and playback information"""
        print("TimeSync.getInfo()")
        return self.infoMsg
        


