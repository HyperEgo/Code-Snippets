# Get html tags, support to parser, std lib python 2.6.6

from HTMLParser import HTMLParser

# class def
"""
Collect html tags, print to std out
"""
class IdHtmlTags(HTMLParser):
	def __init__(self):
		HTMLParser.__init__(self)
		self.__text = []
		self.store = []  # define container to store tags
		
	def handle_starttag(self, tag, attrs):
		# print "Begin tag = %s" % tag
		if tag not in self.store:
			self.store.append(tag)  # store tag, ignore repetition
	def handle_endtag(self, tag):
		# print "End tag = %s" % tag
		if tag not in self.store:
			self.store.append(tag)
	def print_store(self):
		self.store.sort()  # sort container alphabetically
		print ' '
		print 'Total tag number = %s' % len(self.store)  # print container size
		print ' '
		print 'Tag contents\n%s' % '\n'.join(map(str, self.store))  # print container contents
		
	def text(self):
		return ''.join(self)
		
		
# funct def
"""
Process html tags
Support method to IdHtmlTags class
"""
def getHtmlTags(text):
	try:
		parser = IdHtmlTags()
		parser.feed(text)
		parser.print_store()
		parser.close()
	except Exception as e:
		print(e)
		
# main
def main():
	infile = open('./html-sample.txt','r')  # open file to read status
	intext = infile.read()  # read file to data object
	infile.close()  # close file
	getHtmlTags(intext)  # pass data object to parser
	
if __name__ == '__main__':
	main()
	
