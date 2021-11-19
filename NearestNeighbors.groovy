//Nearest neighbor between full classes. 0.2.0.
//Essentially replaced by "Detect centroid distances 2D" command.

//Would need modifications for base classes.
//Note, summary measurements are by default turned off. Uncomment the bottom section.
//Reason: with 27 classes this leads to over 700 annotation level summary measurements, YMMV

imageData = getCurrentImageData()
server = imageData.getServer()
def metadata = getCurrentImageData().getServer().getOriginalMetadata()
def pixelSize = metadata.pixelCalibration.pixelWidth.value
maxDist = Math.sqrt(server.getHeight()*server.getHeight()+server.getWidth()*server.getWidth())

classes = new ArrayList<>(getDetectionObjects().collect {it.getPathClass()?.getBaseClass()} as Set)
print "Classes found: " + classes.size()
cellsByClass = []

classes.each{c->
    cellsByClass << getCellObjects().findAll{it.getPathClass() == c}
}
print "Beginning calculations: This can be slow for large data sets, wait for 'Done' message to prevent errors."
def near = 0.0
for (i=0; i<classes.size(); i++){
    cellsByClass[i].each{c->
        nearest = []
        for (k=0; k<classes.size(); k++){
            near = maxDist
                //cycle through all cells of k Class finding the min distance
            cellsByClass[k].each{d->
                dist = Math.sqrt(( c.getNucleusROI().getCentroidX() - d.getNucleusROI().getCentroidX())*(c.getNucleusROI().getCentroidX() - d.getNucleusROI().getCentroidX())+( c.getNucleusROI().getCentroidY() - d.getNucleusROI().getCentroidY())*(c.getNucleusROI().getCentroidY() - d.getNucleusROI().getCentroidY()))
                if (dist > 0){
                    near = Math.min(near,dist)
                }
            }
            c.getMeasurementList().putMeasurement("Nearest "+ classes[k].toString(), near*pixelSize)
        }
    }
}
//Make measurements for Annotations
//This generates a MASSIVE list if you have many classes. Not recommended for export if there are more than 3-4 classes.
/*
getAnnotationObjects().each{anno->
    //Swap the below "classList" with "baseClasses" to get distances between all base classes
    classes.each{c->
        cellsOfOneType = anno.getChildObjects().findAll{it.getPathClass() == c}
        if (cellsOfOneType.size()>0){
        classes.each{s->
            currentTotal = 0
            cellsOfOneType.each{
                currentTotal += measurement(it, "Nearest "+ s.toString())
            }
            anno.getMeasurementList().putMeasurement("Mean distance in µm from "+s.toString()+" to nearest "+c.toString(),currentTotal/cellsOfOneType.size())
        }
    }}
}
*/

print "Done"