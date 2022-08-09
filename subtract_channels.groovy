//script to subtract channels within an image and save it in a folder

import qupath.opencv.tools.OpenCVTools
import qupath.opencv.tools.GroovyCV
import org.bytedeco.opencv.opencv_core.Size
import org.bytedeco.opencv.opencv_core.Mat
import static org.bytedeco.opencv.global.opencv_core.*
import static org.bytedeco.opencv.global.opencv_imgproc.*
import qupath.lib.images.servers.TransformedServerBuilder
import javax.imageio.ImageIO
import qupath.opencv.ops.ImageOps

//get image data
def imageData = getCurrentImageData()
//get server to access all metadata
def server = getCurrentServer()
double downsample = 1
def region = RegionRequest.createInstance(server.getPath(), downsample, 0, 0, server.getWidth(), server.getHeight())
def img = server.readBufferedImage(region)
def imageExportType = 'tif'

//create a folder called 'channel_subtract' which will store the subtracted channel
def pathOutput = buildFilePath(PROJECT_BASE_DIR, 'channel_subtract')
mkdirs(pathOutput)
def name = GeneralTools.getNameWithoutExtension(getProjectEntry().getImageName())
name = name+"_subtract"

//OpenCV is being used for performing the image acrobatics
// Convert to an OpenCV Mat, then apply a difference of Gaussians filter
def mat = OpenCVTools.imageToMat(img)

//split image channels
mat_ch = OpenCVTools.splitChannels(mat)

//assign channel to variable
cy3_mat = mat_ch[2]
cy5_mat = mat_ch[3]

//create a variable that holds the subtracted image mat
subtract_mat = GroovyCV.minus(cy3_mat, cy5_mat)

//convert the opencv mat back into a Java buffered image
subtract_buff_img = OpenCVTools.matToBufferedImage(subtract_mat)
def fileImage = new File(pathOutput, name + '.' + imageExportType.toLowerCase())
ImageIO.write(subtract_buff_img, imageExportType, fileImage)

//use concatenate_channel_image.groovy script to add this channel to the original image
