cells = getCellObjects();
def toRemove = cells[0].getMeasurementList().getMeasurementNames().findAll { it.contains("Ranking") }
removeMeasurements(qupath.lib.objects.PathCellObject, toRemove as String[])
fireHierarchyUpdate()