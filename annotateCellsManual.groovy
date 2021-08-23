selected = getSelectedObjects()
def Class = getPathClass('immune cells: Myeloid cells')
for (def detection in selected){
detection.setPathClass(Class)
}
fireHierarchyUpdate()
