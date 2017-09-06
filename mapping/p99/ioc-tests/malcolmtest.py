import unittest
from malcolm.core import Process
from malcolm.modules.ADAndor.blocks import andor_detector_driver_block


class MalcolmTestCase(unittest.TestCase):
    def setUp(self):
        self._process = Process("malcolm-test")
        self._process.start()

    def tearDown(self):
        self._process.stop()
