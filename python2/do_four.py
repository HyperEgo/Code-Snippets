# do twice modification, takes function and string as parms

def print_spam(v):
    print(v)

def do_twice(f,v):
    f(v)
    f(v)

def do_four(f,v):
    do_twice(f,v)
    do_twice(f,v)

#do_twice(print_spam, "spam")
do_four(print_spam, "print four times")

