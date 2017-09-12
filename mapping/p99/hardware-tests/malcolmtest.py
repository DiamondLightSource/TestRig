import unittest
from malcolm.core import Process
from malcolm.modules.ADAndor.blocks import andor_detector_driver_block


class MalcolmTestCase(unittest.TestCase):
    def init_blocks(self):
        pass

    def setUp(self):
        self.init_malcolm_process()
        self.init_block_factory()
        self.init_blocks()
        self.start_malcolm_process()

    def tearDown(self):
        self.stop_malcolm_process()

    def init_malcolm_process(self):
        self._process = Process("malcolm-test")

    def init_block_factory(self):
        self._block_factory = MalcolmBlockFactory(self._process)

    def start_malcolm_process(self):
        self._process.start()

    def stop_malcolm_process(self):
        self._process.stop()

    def assert_almost_equal(self, expected, actual):
        self.assertAlmostEqual(expected, actual, 3)


class MalcolmBlockFactory:
    def __init__(self, process):
        self._process = process

    def make_andor_driver_block(self, mri, prefix):
        return self.make_block(andor_detector_driver_block, {
            "mri": mri,
            "prefix": prefix
        })

    def make_block(self, block_function, params):
        block_function(self._process, params)
        return self._process.block_view(params["mri"])