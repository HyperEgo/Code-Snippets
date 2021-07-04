# Parse html code, std lib python 2.6.6

from HTMLParser import HTMLParser
from re import sub
from sys import stderr
from tracebook import print_exc

# class def
"""
Parse html code, convert to strings
Incorporate get url, user authentication logic
"""
class CustomHtmlParser(HTMLParser):

	def __init__(self):
		HTMLParser.__init__(self)  # initialize base class
		self.__text = []  # clear text value
		
	def handle_data(self, data):
		text = data.strip()  # ignore whitespace
		if len(text) > 0:
			text = sub('[ \t\r\n]+', ' ', text)
			self.__text.append(text + ' ')

	def handle_starttag(self, tag, attrs):  # add start tags
		if tag == 'p':  # enumerate tags, add case logic, streamline decision tree
			self.__text.append('\n')
		elif tag == 'br':
			self.__text.append('\n')		
		elif tag == 'body':
			self.__text.append('\n')		
		elif tag == 'center':
			self.__text.append('\n')		
		elif tag == 'dd':
			self.__text.append('\n')		
		elif tag == 'div':
			self.__text.append('\n')		
		elif tag == 'dl':
			self.__text.append('\n')		
		elif tag == 'dt':
			self.__text.append('\n')		
		elif tag == 'fieldset':
			self.__text.append('\n')		
		elif tag == 'font':
			self.__text.append('\n')		
		elif tag == 'head':
			self.__text.append('\n')		
		elif tag == 'html':
			self.__text.append('\n')		
		elif tag == 'image':
		
	def handle_startendtag(self, tag, attrs):  # add end tags
		if tag == 'br':
			self.__text.append('\n')
			
	def text(self):
		return ''.join(self.__text).strip()
		
# method def
"""
Parse html code, convert to strings
Support method to CustomHtmlParser class
"""
def getParser(text):
	try:
		parser = CustomHtmlParser()
		parser.feed(text)
		parser.close()
		return parser.text()
	except:
		print_exc(file=stderr)
		return text
		
# main
def main():
	infile = open('./html-sample.txt', 'r')  # open file to read status
	intext = infile.read()  # read file to data object
	infile.close()  # close file
	print(getParser(intext))  # pass data object to parser, print to std out

if __name__ '__main__':
	main()
	
