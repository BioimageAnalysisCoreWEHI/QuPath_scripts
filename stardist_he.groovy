import qupath.tensorflow.stardist.StarDist2D

// Specify the model directory (you will need to change this!)
def pathModel = '/home/ninatubau/he_heavy_augment'

def stardist = StarDist2D.builder(pathModel)
        .threshold(0.1)              // Prediction threshold
        .normalizePercentiles(1, 99) // Percentile normalization
        .pixelSize(0.5)              // Resolution for detection
        .includeProbability(true)    // Include prediction probability as measurement
        .measureIntensity()          // Add cell measurements (in all compartments)
        .cellExpansion(5.0)          // Approximate cells based upon nucleus expansion
        .build()

// Run detection for the selected objects
def imageData = getCurrentImageData()
def pathObjects = getSelectedObjects()
if (pathObjects.isEmpty()) {
    Dialogs.showErrorMessage("StarDist", "Please select a parent object!")
    return
}
stardist.detectObjects(imageData, pathObjects)
println 'Done!'