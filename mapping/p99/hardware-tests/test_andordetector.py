import unittest
from test_areadetector import AreaDetectorTestCase


class AndorDetectorTestCase(AreaDetectorTestCase):
    __test__ = True

    def init_blocks(self):
        self._block_factory.load_blocks("test-yaml-configs/bl99p-ea-det-01.yaml")
        self._camera = self._process.block_view("ANDOR:DRV")
