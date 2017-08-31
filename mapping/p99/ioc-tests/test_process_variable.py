import unittest
from process_variable import *

DEFAULT_TEST_PV_NAME = "testname"


class TestPvMembers(unittest.TestCase):
    def test_constructor_initializes_name(self):
        pv = ProcessVariable(DEFAULT_TEST_PV_NAME)
        self.assertEqual(DEFAULT_TEST_PV_NAME, pv.get_name())
