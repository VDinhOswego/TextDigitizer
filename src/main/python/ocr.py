import numpy as np
import cv2
try:
    from PIL import Image
except ImportError:
    import Image
import pytesseract
import sys
import getopt
import os

if os.name == "nt":
    pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract'

fileName = "test.jpg"

try:
    opts, args = getopt.getopt(sys.argv[1:], "hf:", ["file="])
except getopt.GetoptError:
    print("-f filename")
    sys.exit(2)
for opt, arg in opts:
    if opt == "-h":
        print("-f filename")
        system.exit()
    elif opt in ("-f", "--file"):
        fileName = arg

sharpKernel = np.array([[0,-1,0],
                        [0,3,0],
                        [0,-1,0]])

img = cv2.imread(fileName)
img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
img = cv2.filter2D(img, -1, sharpKernel)
img = cv2.bilateralFilter(img,11,75,75)

#img = cv2.GaussianBlur(img, (7,7), 0)
#img = cv2.adaptiveThreshold(img,255,cv2.ADAPTIVE_THRESH_GAUSSIAN_C,cv2.THRESH_BINARY,11,2)
img = cv2.threshold(img, 0, 255, cv2.THRESH_TRUNC | cv2.THRESH_OTSU)[1]
#cv2.THRESH_TRUNC cv2.THRESH_BINARY
#img = cv2.fastNlMeansDenoising(img, None, 10, 111, 21)

##cv2.namedWindow("i", cv2.WINDOW_NORMAL)
##cv2.imshow("i", img)
##cv2.waitKey(0)
##cv2.destroyAllWindows()

print(pytesseract.image_to_string(img, config="--psm 1 -c preserve_interword_spaces=1"))

#print(pytesseract.image_to_string(Image.open('test.JPG')))

#print(pytesseract.image_to_boxes(Image.open('test.JPG')))
