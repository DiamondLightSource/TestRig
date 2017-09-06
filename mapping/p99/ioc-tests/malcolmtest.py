import unittest
from malcolm.core import Process


class MalcolmTestCase(unittest.TestCase):
    def setUp(self):
        self._process = Process("malcolm-test")
        self._process.start()

    def tearDown(self):
        self._process.stop()