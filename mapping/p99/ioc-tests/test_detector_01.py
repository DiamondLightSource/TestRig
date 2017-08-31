from pkg_resources import require
require('cothread==2.13')
from cothread import catools
import unittest


DETECTOR_MODEL = "DC-152Q-C00-FI"

class TestDetector(unittest.TestCase):
    def setUp(self):
        pass

class TestIoc(unittest.TestCase):
    def test_ioc_active(self):
        print caget("BL99P-EA-DET-01PortName_RBV")
    def test_ioc_camera_connected(self):
        self.assertEqual(caget("BL99P-EA-DET-01Model_RBV"), DETECTOR_MODEL, "The IOC has not detected the camera")

class TestCapture(unittest.TestCase):
    #def test_
    pass

def caget(pv_names):
    return catools.caget(pv_names)

def caput_async(pv_names, values):
    catools.caput(pv_names, values)

def caput_sync(pv_names, values):
    catools.caput(pv_names, values, wait=True)