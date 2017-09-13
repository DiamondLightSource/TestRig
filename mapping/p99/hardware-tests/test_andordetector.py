import unittest
from test_areadetector import AreaDetectorTestCase

ANDOR_DEFAULTS_SAVE = "ANDOR-DEFAULTS"

class AndorDetectorTestCase(AreaDetectorTestCase):
    __test__ = True

    def init_blocks(self):
        self._block_factory.load_blocks("test-yaml-configs/bl99p-ea-det-01.yaml")
        self._detector = self._process.block_view("ANDOR")
        self._camera = self._process.block_view("ANDOR:DRV")

    def save_state(self):
        self._detector.save(ANDOR_DEFAULTS_SAVE)

    def restore_state(self):
        self._detector.design.put_value(ANDOR_DEFAULTS_SAVE)
        #self.fail(self._detector.state)