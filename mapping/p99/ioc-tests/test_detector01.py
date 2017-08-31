import unittest
from cacommon import *
from processvariable import *

DETECTOR_MODEL = "DC-152Q-C00-FI"
PV_MODEL_NAME = ProcessVariable("BL99P-EA-DET-01Model_RBV")
PV_PORT_NAME = ProcessVariable("BL99P-EA-DET-01PortName_RBV")
PV_DATA_TYPE = ProcessVariable("BL99P-EA-DET-01DataType", "UInt16")
PV_COLOR_MODE = ProcessVariable("BL99P-EA-DET-01ColorMode", "Mono")
PV_GAIN = ProcessVariable("BL99P-EA-DET-01Gain", 1.00)

def reset_cam_to_defaults():
    set_pvs_to_defaults([
        PV_DATA_TYPE,
        PV_COLOR_MODE
    ])


class TestDetector(unittest.TestCase):
    def setUp(self):
        reset_cam_to_defaults()


class TestIoc(unittest.TestCase):
    def test_ioc_active(self):
        print get_pv_value(PV_PORT_NAME)

    def test_ioc_camera_connected(self):
        self.assertEqual(get_pv_value(PV_MODEL_NAME), DETECTOR_MODEL, "The IOC has not detected the camera")


class TestCapture(unittest.TestCase):
    # def test_
    pass
