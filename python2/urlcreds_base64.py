# Get url request, std lib python 2.6.6
# Get credentials using base64 encoding, authentication
# Tested on protected systems

import urllib2, base64
import getpass
import urllib import urlopen

def main():

	""" 
	Start url request, add regex, validation, abort logic
	""" 
	url = "http://some-unknown-website.org"  # get raw url
	request = urllib2.Request(url)  # exchange url to request object
	
	"""
	Get user credentials
	"""
	print("Please enter credentials.")
	username = raw_input("Username: ")
	password = getpass.getpass("Password: ")
	# print("Print creds -> username: [%s], password [%s]" % (username, password))

	"""
	Wrap username, password with base64 encoding
	Add base64 header to request url object
	"""
	base64string = base64.encodestring('%s:%s' % (username, password)).replace('\n', '')  # apply base64 encoding
	request.add_header("Authorization", "Basic %s" % base64string)  # add base64 header

	"""
	Open request url, add parser logic, output data
	"""
	result = urllib2.urlopen(request)  # open url for reading using base64 encoding, authenticated creds
	data = result.read()  # process request url for data read
	print(data)  # pass data to parser, print to std out or write to file
	
if __name__ == '__main__':
	main()	
