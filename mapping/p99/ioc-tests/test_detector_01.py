import unittest
from ca_common import *

DETECTOR_MODEL = "DC-152Q-C00-FI"
PV_MODEL_NAME = "BL99P-EA-DET-01Model_RBV"
PV_PORT_NAME = "BL99P-EA-DET-01PortName_RBV"


class TestDetector(unittest.TestCase):
    def setUp(self):
        pass


class TestIoc(unittest.TestCase):
    def test_ioc_active(self):
        print caget(PV_PORT_NAME)

    def test_ioc_camera_connected(self):
        self.assertEqual(caget(PV_MODEL_NAME), DETECTOR_MODEL, "The IOC has not detected the camera")


class TestCapture(unittest.TestCase):
    # def test_
    pass
