# print table graphic of columns and rows

"""
print row of symbols
vertex = number of symbols, in between
edge = number of symbols, at edges
vSymbol = vertex symbol
eSymbol = edge symbol
"""
def print_row(vertex, edge, vSymbol, eSymbol):
    topCnt = edge
    while(topCnt>0):
        topCnt = topCnt - 1
        print(end=eSymbol)
        midCnt = vertex
        while(midCnt>0):
            midCnt = midCnt - 1
            print(end=vSymbol)
    print(eSymbol)


"""
print table of symbols (alternating rows)
pass arrays with row, column count data & character symbols
"""
def print_table(rowNumberList, symbolNumberList, charListA, charListB):	
    columns = symbolNumberList[1]  # column count, semantic
    topCnt = rowNumberList[0]  # row count
    while(topCnt>0):
        topCnt = topCnt - 1
        print_row(symbolNumberList[0], columns, charListA[0], charListA[1])  # print top row(s)

        midCnt = rowNumberList[1]  # row thickness
        while(midCnt>0):
            midCnt = midCnt - 1
            print_row(symbolNumberList[0], columns, charListB[0], charListB[1])  # print middle row(s)
    
    # print bottom row, finisher
    print_row(symbolNumberList[0], symbolNumberList[1], charListA[0], charListA[1])

# start declarations
rowNumberList = [2, 8]  # row count, row thickness
symbolNumberList = [5, 6]  # number of symbols ea row [vertex, edge], e.g. [4='^', 3='$'] = $^^^^$^^^^$
charListA = ['-', '+']  # symbol character type
charListB = [' ', '|']  # symbol character type

# run (main) print method
print_table(rowNumberList, symbolNumberList, charListA, charListB)

