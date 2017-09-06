import unittest
from malcolm.core import Process
from malcolm.modules.ADAndor.blocks import andor_detector_driver_block


class MalcolmTestCase(unittest.TestCase):
    def setUp(self):
        self._process = Process("malcolm-test")
        self.init_blocks()
        self._process.start()

    def tearDown(self):
        self._process.stop()

    def init_blocks(self):
        pass


class AndorDetectorTestCase(MalcolmTestCase):
    def init_blocks(self):
        self._detector = self.register_driver_block()

    def register_driver_block(self):
        mri = "ANDOR"
        andor_detector_driver_block(self._process, {
            "mri": mri,
            "prefix": "BL99P-EA-DET-01:DET:CAM"
        })
        return self._process.block_view(mri)