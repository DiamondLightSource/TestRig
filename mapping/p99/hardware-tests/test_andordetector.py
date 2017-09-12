import unittest
from test_areadetector import AreaDetectorTestCase


class AndorDetectorTestCase(AreaDetectorTestCase):
    __test__ = True

    def init_blocks(self):
        self._detector = self._block_factory.make_andor_driver_block("ANDOR", "BL99P-EA-DET-01:DET:CAM")
