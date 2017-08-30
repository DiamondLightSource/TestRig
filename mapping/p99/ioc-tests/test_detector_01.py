from pkg_resources import require
require('cothread==2.13')
from cothread import catools
import unittest


DETECTOR_MODEL = "DC-152Q-C00-FI"

def caget(pv_name):
    return catools.caget(pv_name)

class TestIoc(unittest.TestCase):

    def test_ioc_active(self):
        print caget("BL99P-EA-DET-01PortName_RBV")

    def test_ioc_camera_connected(self):
        self.assertEqual(caget("BL99P-EA-DET-01Model_RBV"), DETECTOR_MODEL, "The IOC has not detected the camera")