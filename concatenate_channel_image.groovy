//It opens two images and merges the channels and adds it as a new image within the Qupath Project
// this script is supposed to be used subsequent to creating a subtracted channel image

import javafx.application.Platform
import qupath.lib.images.ImageData
import qupath.lib.images.servers.ImageServerProvider
import qupath.lib.images.servers.TransformedServerBuilder

import java.awt.image.BufferedImage



// Open two images
def server1 = getCurrentServer()
def currWSIName = GeneralTools.getNameWithoutExtension(getProjectEntry().getImageName())
wsi_to_import = currWSIName+"_subract.tif"

def path2 = 'Y:/Bianca for Pradeep/IF Experiment ER and Ki671/QuPath Project File/channel_subtract/'+wsi_to_import
def server2 = ImageServerProvider.buildServer(path2, BufferedImage)



// Merge the images by concatenating channels
def serverMerged = new TransformedServerBuilder(server1)
    .concatChannels(server2)
    .build()

// Open in the current viewer
def imageData = new ImageData<BufferedImage>(serverMerged)
Platform.runLater {
    getCurrentViewer().setImageData(imageData)
}
