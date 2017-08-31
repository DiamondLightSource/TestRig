import unittest
from ca_common import *

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

