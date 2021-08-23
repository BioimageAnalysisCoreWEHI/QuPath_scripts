def cells = QPEx.getCellObjects()
def pathClass = getPathClass('')

for (cell in cells) {
    cell.setPathClass(pathClass)
}