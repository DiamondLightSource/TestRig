import unittest
from processvariable import *

DEFAULT_PV_NAME = "testname"
DEFAULT_PV_DEFAULT_VALUE = 123



class TestPvMembers(unittest.TestCase):
    def test_constructor_initializes_name(self):
        pv = ProcessVariable(DEFAULT_PV_NAME)
        self.assertEqual(DEFAULT_PV_NAME, pv.get_name())

    def test_constructor_initializes_default_value(self):
        pv = ProcessVariable(DEFAULT_PV_NAME, DEFAULT_PV_DEFAULT_VALUE)
        self.assertEqual(DEFAULT_PV_DEFAULT_VALUE, pv.get_default_value())