# **Assignment 6 - Image Processing**

### **Contributors** <br>

#### Aryan Kale - Khoury College, Northeastern University <br>

#### Anshul Shirude - Khoury College & College of Science, Northeastern University <br>

# **Controller** <br>

The controller had all the commands embedded inside that package.
The responsibility of the controller is to do the operations on the user frontier.

### **ImageCommand interface** <br>

The ImageCommand interface represented the operations that can be done on an image within a model.

### **AbstractImageCommand Class** <br>

An abstract class containing fields common to all commands. It has a constructor which takes in
2 parameters representing the file path that we are loading from and saving to.

<br>

#### Commands to perform from the controller side of things.

These methods take in the model and call the appropriate method for the command on the model.

* Blur
* Brighten
* Darken
* Flip
* GreyscaleColorTransformation
* GreyscaleComponent
* Load
* Save
* Sepia
* Sharpen

<br>

### **ImageInputOutput interface** <br>
This interface handles the loading and the saving of the images directed to the 
AbstractImageInputOutput class which is responsible for loading the image and then saving it 
appropriately. You are allowed to load and save the images in four different formats: ASCII PPM, 
bmp, jpg and png. 

Classes that represent the different file formats to load and save.
* BMPImageInputOutput
* JPGImageInputOutput
* PPMImageInputOutput
* PNGImageInputOutput

### **ImageGUIController Interface**
This interface represents a controller for a GUI which interprets user input through the
GUI and then delegates tasks to other components of the application.

### **ImageGUIControllerImpl Class**
An implementation of a controller for the GUI representation of this program. It has a model
and a GUI view, and it is an action listener itself for every part of the GUI view which has
action commands. It handles all the potential user input through the GUI view in a proper
manner by delegating tasks to the model and displaying things on the GUI view. This
controller only keeps track of one image at a time even though the model has the capability to
keep track of many, which is why every image is loaded to the model with a name of "guiImage"
and every time a manipulation is done, it is saved with this name, overriding the previous
image. Users only have access to the image on the screen and cannot undo or go back to
previous iterations, but they can load in the image again if they'd like to go back.

### **ImageTextController Interface**
* This interface represents a controller for the image application which handles input and 
properly delegates tasks to the other components of the application.

### **ImageTextControllerImpl Class**
* A simple controller implementation which uses the model and view and a specific readable to
handle input and delegate tasks. It also contains a map of potential commands.
<br>


### **Script for running the program** <br>

Instructions on how to perform image manipulations on the family.ppm file located in the res/
folder.

In order to run this script using our program, one can run the main method within the
ImageProcessingProgram class and enter the scripts in the console. All commands should be
entered on a new line. One example of loading and saving the family image from a ppm to a ppm, 
png, jpg, and bmp is displayed below. The same rules apply if you want to load from a different 
format and save in a different format.

The #s represent explanations of what the scripts themselves do.

<br>

#load family.ppm and call it 'family' <br>
`load res/family/family.ppm family`

#brighten family by adding 10 to the components of the pixels (must be value greater than 0 to
brighten) <br>
`brighten 10 family family-brighter`

#darken family by subtracting 10 to the components of the pixels (must be value greater than 0 to
darken) <br>
`darken 10 family family-darker`

#flip family vertically <br>
`vertical-flip family family-vertical`

#flip family horizontally <br>
`horizontal-flip family family-horizontal`

#flip the vertically-flipped family horizontally <br>
`horizontal-flip family-vertical family-vertical-horizontal`

#create a greyscale using only the red component, as an image family-red-greyscale <br>
`red-component family family-red-greyscale`

#create a greyscale using only the green component, as an image family-green-greyscale <br>
`green-component family family-green-greyscale`

#create a greyscale using only the blue component, as an image family-blue-greyscale <br>
`blue-component family family-blue-greyscale`

#create a greyscale using only the value component, as an image family-value-greyscale <br>
`value-component family family-value-greyscale`

#create a greyscale using only the intensity component, as an image family-intensity-greyscale <br>
`intensity-component family family-intensity-greyscale`

#create a greyscale using only the luma component, as an image family-luma-greyscale <br>
`luma-component family family-luma-greyscale`

#blur the image of the family <br>
`blur family family-blurred`

#sharpening the image of the family <br>
`sharpen family family-sharpened`

#applying a greyscale to the image of the family <br>
`greyscale family family-greyscale`

#applying a sepia tone to the image of the family <br>
`sepia family family-sepia`

#save family-brighter in the ppm format in the res/family/ directory <br>
`save res/family/family-brighter.ppm family-brighter`

#save family-brighter in the png format <br>
`save family-brighter.png family-brighter`

#save family-brighter in the jpg format <br>
`save family-brighter.jpg family-brighter`

#save family-brighter in the bmp format <br>
`save family-brighter.bmp family-brighter`

#save family-value-greyscale <br>
`save res/family/family-gs.ppm family-value-greyscale`

#Save the family blurred image <br>
`save family-blurred.ppm family-blurred`

#Save the family sharpened image <br>
`save family-sharpened.ppm family-sharpened`

#Save the family with the greyscale image color transformation <br>
`save family-greyscale.ppm family-greyscale`

#Save the family with the sepia image color transformation <br>
`save family-sepia.ppm family-sepia`

#overwrite family in the model with another file <br>
`load res/Square.ppm family`

#q to quit the program <br>
`q`
<br>
<br>

# **Model** <br>

## **Enums** <br>

There are 2 different types of enums stored in this package.

* FlipType can be one of:
    * Horizontal flip
    * Verical flip
* GreyscaleComponentType can be one of:
    * Red
    * Green
    * Blue
    * Value
    * Intensity
    * Luma

## **Image** <br>

### **Image interface** <br>

The interface which contains methods for returning properties of an image and methods for creating
new images with manipulations done.

### **ImageImpl class** <br>

The ImageImpl class implements the Image interface. It contains the logic for flipping,
darkening, brightening, applying a greyscale component, filter, and color transformation to the 
images appropriately.

### **Pixel interface** <br>

This interface represents the methods that can be done on the Pixels to find the RGB properties
of each of the pixels.

### **RGBPixel class** <br>

This class implements the Pixel interface which has fields for each of the RGB components of a 
pixel and returns the RGB value for each of the pixels in the corresponding pixel.

## **ImageModel interface** <br>

An interface representing the model for this image application which is able to store multiple
images and apply manipulations on these images. It contains methods to observe different images in
the application, to deal with input and output, and to manipulate the images and store the new
manipulated versions differently.

### **ImageModelImpl class** <br>

A class representing an implementation of the ImageModel which uses a hashmap to store a map of
image names to images themselves. It implements the methods of the ImageModel.

<br>
<br>

# **View** <br>

### **ImageGUIView interface** <br>
An interface that represents a GUI view for the Image Processing Application. It contains
methods to update what is currently being shown on the screen and render messages to the
user or prompt them for more input, amongst other methods.

### **ImageGUIViewImpl Class** <br>
An implementation of an ImageGUIView which uses JSwing components to put together the specific
GUI. This GUI consists of buttons that deal with IO on images, buttons for manipulations on
images, and sections to display an image and a histogram representing the component
information of that image. If the image is bigger than the space allotted, then the image will  
become scrollable.

### **Histogram Interface** <br>
An interface to represent a histogram for an image which displays relative frequencies
of specific aspects or components of the image. It contains methods to update the histogram's
contents to represent a specified image and to physically draw the histogram itself.

### **HistogramImpl Class** <br>
An implementation of a histogram, which is a JPanel, and uses arrays to keep track of the
relative frequencies of the red, green, blue, and intensity values. It keeps track of the
frequencies of these specific values from 0-255, which are the possible values for each of
these components, and draws the histogram onto itself (a JPanel).

### **ImageView interface** <br>
An interface representing a view for the program, capable of displaying information to the user.

### **ImageTextView class** <br>
A class representing an implementation of the ImageView of an image which displays information to
the user through text.

<br>
<br>

# **ImageProcessingProgram** <br>

The main method class that is used to run the program. It takes in the model, view, and the
readable which is plugged in to the controller. A method is called on the controller to start
the image manipulation program.

<br>
<br>

### **Image Disclaimer Note** <br>
The pictures that were used in this assignment were all created by us and are self owned images.
We authorize the use of this image in this project.
<br>
<br>

### **Assignment Requirements** 

We were able to finish everything that was asked of us for this assignment, and all parts of our 
program are complete.

The res/family/ folder contains an original image of a family in .ppm, .png, .bmp, and .jpg format. 
It also contains the same image after it has been blurred, sharpened, greyscaled, sepia-ed, and 
downsized. There are 2 examples of the image downsized - one is with the same width and height 
ratio with one as the width and height ratio altered.

The res/scripts folder contains two different scripts. One is to be used with IntelliJ, and the 
other is to be used with the .jar file in the terminal. Directions on how to use these are 
located in the USEME file.

The .jar file is located in the res/ folder.
<br>
<br>

### **Changes and Additions in Design** <br>
* The loading and saving of the images was originally done in the model, but now switched to 
  become the job of the controller by creating a new interface for all IO.
  * This was a mistake that we made in the last assignment, since handling all IO is the job of 
    the controller, not the model. We were able to extract this logic to a new interface which 
    is a part of the controller and this allowed us to have our model not deal with IO, since it 
    didn't really make sense for the model to deal with file paths directly. The interface for IO 
    also allowed us to support IO on many different types of images very easily, and made adding 
    support for new image types very easy as well. 
  * This also required us to define a new method in the model interface, setImage that 
    would allow the model to store an image with a specific name, because after loading an image,
    it was sent to the model and there had to be some way to now store it, without access to its 
    fields.
* New functionality was added to support filtering images (blurring and sharpening).
* New functionality was added to support color transformations on images (greyscale and a 
    sepia tone transformation).
  * In order to add the filtering and color transformation functionality, we had to add methods 
    to the ImageModel and Image interface to support this. We felt as if this was a justified 
    addition because it now allows any user to have access to all the features and possible 
    image manipulations of our program when they use it, instead of only having access to some 
    of the features, or only the older manipulations. Adding these methods didn't affect any 
    other parts of our design, and the way we implemented them made adding future color 
    transformations and filters very simple.
  * Since we already had greyscale-esque functionality from the previous assignment, and we were 
    asked to implement a greyscale as a color transformation, we changed their names within the 
    program to be more specific. The new command names are GreyscaleComponent for the previous 
    greyscale manipulation that highlights a specific component, and 
    GreyscaleColorTransformation for the one that is a color transformation. They have the same 
    names in the model interface as well.
* There is the ability to load and save conventional file formats (bmp, jpg and png) in addition 
  to ASCII ppm files.
* You may specify the command line options in the script file and run the script file with all 
  the different commands.
* Supports the ability to switch between file formats. You can load in one file format 
  and save in a different file format.
* Supports the ability to create a JAR file and run the program with the jar file specifications 
  from terminal.

<br>
<br>

### **Updated Changes and Additions in Design** <br>
* New addition of a Graphical User Interface (GUI)
  * The GUI displays the image that is currently being worked on. 
  * In the case that the image is bigger than the area allocated to it in the GUI, a scrollbar 
  appears to scroll the image.
  * Live feedback support is provided to the GUI. Any changes that are made to the image are 
    directly visible on the GUI.
  * User interface exposes the features of the program as buttons. The features supported are:
    * load (one image at a time)
    * save (one image at a time)
    * brighten
    * darken
    * horizontal flip
    * vertical flip
    * red component
    * green component
    * blue component
    * value component
    * intensity component
    * luma component
    * blur
    * sharpen
    * greyscale
    * sepia
    * downsize (new addition read below)
  * When saving an image, the user sees which image they are saving and must include one of 4 
    file extensions (ppm, jpg, png, bmp) to the end of the saving name.
  * The user is provided with the ability to load and save whichever image they want from their 
    local machine.
  * Error conditions are displayed to the user through pop-up messages.
* Histogram
  * Histogram displays the red, green, blue, and intensity components of the image below the 
       image.
  * When the image is manipulated, the histogram automatically refreshes to display the components.
  * Red line color on the histogram = red component
  * Green line color on the histogram = green component
  * Blue line color on the histogram = blue component
  * Black line color on the histogram = intensity component
* Downsizing an image
  * A user has the ability to downsize their image. They do so by specifying a percent to downsize 
 the width by and a percent to downsize the height by. For example, entering 20 for the width 
  percent and 30 for the height percent will return an image which is 80% of its original width and
  70% of its original height. 
  * The user is asked to specify each of these percents in the GUI and suitable error messages are
  displayed if necessary.
  * In order to incorporate this into our model, we added a new method in the ImageModel 
  interface and implementing classes, named downsize. This method worked the same as other 
  manipulations. It takes in two percents to downsize by and calls the downsize method on the 
  specific image. This meant that implementing a downsize method in the Image interface and 
  implementing classes which contained all the logic to perform the actual downsizing. Even though 
  this meant changing previous code, we believe that it is justified because these newly added 
  components only work with each other and none of the preexisting code, so there was no chance
  that it would affect anything else. It also allowed us to ensure that a user has access to all the
  manipulations at once.

* Command Line Support
  * `java -jar Assignment6.jar -file path-of-script-file`: The program opens the 
    script file, executes it, and then shuts down.
  * `java -jar Assignment6.jar -text`:  The program opens an interactive text mode, 
  allowing the user to type the script and execute it one line at a time.
  * `java -jar Assignment6.jar`: The program opens the GUI.
<br>
<br>

### **Contact** <br>
#### Aryan Kale - kale.ar@northeastern.edu <br>
#### Anshul Shirude - shirude.a@northeastern.edu <br>
