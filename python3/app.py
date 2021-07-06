#!/usr/bin/python3
"""Invoke interpreter implicitly, verify execute permissions => ./PYTHON_APP.py"""

from TimeSyncPublisher import TimeSyncPublisher
from ClientSubscriber import ClientSubscriber

# main
def main():
    pub = TimeSyncPublisher()
    sub = ClientSubscriber(pub)
    pub.pullTrigger()

# main exec thread
if __name__ == '__main__':
   main() 
