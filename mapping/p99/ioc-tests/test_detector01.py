import unittest
from malcolmtest import *
from malcolm.modules.ADAndor.blocks import andor_detector_driver_block


class TestCamera(MalcolmTestCase):
    def test_set_exposure(self):
        andor_detector_driver_block(self._process, {
            "mri": "ANDOR",
            "prefix": "BL99P-EA-DET-01:DET:CAM"
        })
        detector = self._process.block_view("ANDOR")
        detector.exposure.put_value(0.5)
        self.assertAlmostEqual(0.5, detector.exposure.value, 4)