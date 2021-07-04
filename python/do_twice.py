#function parm pass, do twice

def do_twice(f):
    f()
    f()

def print_spam():
    print("spam")

do_twice(print_spam)
    
