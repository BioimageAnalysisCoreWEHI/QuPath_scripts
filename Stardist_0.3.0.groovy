import qupath.ext.stardist.StarDist2D

// Specify the model file (you will need to change this!)
var pathModel = '/home/ninatubau/he_heavy_augment.pb'

var stardist = StarDist2D.builder(pathModel)
        .threshold(0.5)              // Probability (detection) threshold
        .normalizePercentiles(1, 99) // Percentile normalization
        .pixelSize(0.5)              // Resolution for detection
        .cellExpansion(5.0)          // Approximate cells based upon nucleus expansion
        .cellConstrainScale(1.5)     // Constrain cell expansion using nucleus size
        .measureShape()              // Add shape measurements
        .measureIntensity()          // Add cell measurements (in all compartments)
        .includeProbability(true)    // Add probability as a measurement (enables later filtering)
        .build()

// Run detection for the selected objects
var imageData = getCurrentImageData()
var pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Detection done!'

var min_size = 40
var max_size = 250
def toDelete = getDetectionObjects().findAll {measurement(it, 'Cell: Area µm^2') < min_size || measurement(it, 'Cell: Area µm^2') > max_size}

removeObjects(toDelete, true)
println toDelete.size() + ' cells deleted based on size'