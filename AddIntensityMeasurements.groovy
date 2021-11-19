import qupath.lib.analysis.features.ObjectMeasurements
import qupath.lib.images.ImageData
import qupath.lib.images.servers.ImageServerMetadata
import qupath.lib.images.servers.TransformedServerBuilder


def imageData = getCurrentImageData()

def server = imageData.getServer()
def measurements = ObjectMeasurements.Measurements.values() as List
def compartments = ObjectMeasurements.Compartments.values() as List //just for cells
def downsample = 1.0
for (detection in getDetectionObjects()) {
  ObjectMeasurements.addIntensityMeasurements(
      server, detection, downsample, measurements, compartments
      )
}