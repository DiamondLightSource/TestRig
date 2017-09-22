# Very temporary script here because the webcam is currently compatible with
# Malcolm and we don't even know for sure if we're using Malcolm, will be replaced

from pkg_resources import require

require('cothread==2.14')

from cothread import catools
from time import sleep
import urllib
import sys
import os

SNAPSHOT_URL = "http://p99-control.diamond.ac.uk:8094/WEB1.mjpg.jpg"

# Pvs
PREFIX = "BL99P-DI-WEB-01:CAM:"
IMAGE_MODE = "%sImageMode" % PREFIX
NUM_IMAGES = "%sNumImages" % PREFIX
ARRAY_COUNTER = "BL99P-DI-WEB-01:MJPG:ArrayCounter"
ARRAY_COUNTER_RBV = "%s_RBV" % ARRAY_COUNTER
ACQUIRE = "%sAcquire" % PREFIX
JPG_URL = "BL99P-DI-WEB-01:MJPG:JPG_URL_RBV"


def capture(path):
    prepare_camera()
    capture_single_frame()
    verify_frame_capture()
    download_frame(path)


def prepare_camera():
    # Set up camera
    print "Setting up camera"
    caput([
        IMAGE_MODE,
        NUM_IMAGES,
        ARRAY_COUNTER
    ], [
        "Single",
        1,
        0
    ])


def capture_single_frame():
    # Capture frame
    print "Capturing a single frame"
    caput(ACQUIRE, 1)
    sleep(2)
    caput(ACQUIRE, 0)


def verify_frame_capture():
    # Check frame captured
    if caget(ARRAY_COUNTER_RBV) != 1:
        print "Failed to capture image"
        sys.exit(1)
    else:
        print "Capture complete"


def download_frame(path):
    # Download captured frame
    print "Downloading frame"
    sleep(2)
    urllib.urlretrieve(SNAPSHOT_URL, path)


def caput(pvs, values):
    catools.caput(pvs, values, wait=True)


def caget(pvs):
    return catools.caget(pvs)


def resolve_pv(name):
    return "%s%s" % (PREFIX, name)


if __name__ == '__main__':
    path = str(sys.argv[1])
    capture(path)
