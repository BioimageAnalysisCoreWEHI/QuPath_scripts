import qupath.lib.gui.tools.MeasurementMapper
import qupath.lib.gui.images.servers.RenderedImageServer

// Define the color map name
String colorMapName = 'Jet'

// Load a color mapper
def colorMapper = MeasurementMapper.loadColorMappers().find {it.name == colorMapName}
println colorMapper

// Define measurement & display range
def name = "Distance to annotation with nothing µm" // Set to null to reset
double minValue = 0.0
double maxValue = 20

// Request current viewer & objects
def viewer = getCurrentViewer()
def options = viewer.getOverlayOptions()
def detections = getDetectionObjects()

// Update the display
if (name) {
    print String.format('Setting measurement map: %s (%.2f - %.2f)', name, minValue, maxValue)
    def mapper = new MeasurementMapper(colorMapper, name, detections)
    mapper.setDisplayMinValue(minValue)
    mapper.setDisplayMaxValue(maxValue)
    options.setMeasurementMapper(mapper)
} else {
    print 'Resetting measurement map'
    options.setMeasurementMapper(null)
}

// Now export the rendered image
import qupath.imagej.tools.IJTools
import qupath.lib.gui.images.servers.RenderedImageServer
import qupath.lib.gui.viewer.overlays.HierarchyOverlay
import qupath.lib.regions.RegionRequest

// It is important to define the downsample!
// This is required to determine annotation line thicknesses
double downsample = 1

// Add the output file path here
String path = buildFilePath(PROJECT_BASE_DIR, 'rendered')
mkdirs(path)

// Request the current viewer for settings, and current image (which may be used in batch processing)
def imageData = getCurrentImageData()

// Create a rendered server that includes a hierarchy overlay using the current display settings
def server = new RenderedImageServer.Builder(imageData)
    .downsamples(downsample)
    .layers(new HierarchyOverlay(null, options, imageData))
    .build()
    
// Write or display the rendered image
int count = 0
for (annotation in getAnnotationObjects()) {
    count++
    def imageName = getProjectEntry().getImageName() + count + 'dist-nothing.png'
    def path2 = buildFilePath(path, imageName)
    def region = RegionRequest.createInstance(server.getPath(), downsample, annotation.getROI())
    writeImageRegion(server, region, path2)
}