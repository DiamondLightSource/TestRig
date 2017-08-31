import unittest
from processvariable import *

FIRST_PV_NAME = "testname"
FIRST_PV_DEFAULT_VALUE = 123
SECOND_PV_NAME = "secondtest"
SECOND_PV_DEFAULT_VALUE = False


class TestProcessVariable(unittest.TestCase):
    def test_constructor_initializes_name(self):
        pv = make_default_pv()
        self.assertEqual(FIRST_PV_NAME, pv.get_name())

    def test_constructor_initializes_default_value(self):
        pv = make_default_pv()
        self.assertEqual(FIRST_PV_DEFAULT_VALUE, pv.get_default_value())


class TestPvHelperFunctions(unittest.TestCase):
    def test_generate_name_list_empty(self):
        pv_list = []
        self.assert_names(pv_list, [])

    def test_generate_name_list_sigular(self):
        pv_list = [make_default_pv()]
        self.assert_names(pv_list, [FIRST_PV_NAME])

    def test_generate_name_list_multiple(self):
        pv_list = make_default_pv_list()
        self.assert_names(pv_list, [FIRST_PV_NAME, SECOND_PV_NAME])

    def test_generate_defaults_lists_empty(self):
        pv_list = []
        self.assert_names_and_defaults(pv_list, [])

    def test_generate_defaults_list_singular(self):
        pv_list = [make_default_pv()]
        self.assert_names_and_defaults(pv_list, [FIRST_PV_DEFAULT_VALUE])

    def test_generate_defaults_list_multiple(self):
        pv_list = make_default_pv_list()
        expected_default_values = [FIRST_PV_DEFAULT_VALUE, SECOND_PV_DEFAULT_VALUE]
        self.assert_names_and_defaults(pv_list, expected_default_values)

    def assert_names(self, pv_list, expected_pv_names):
        self.assertEqual(expected_pv_names, generate_name_list(pv_list))

    def assert_names_and_defaults(self, pv_list, expected_default_values):
        self.assertEqual(expected_default_values, generate_defaults_lists(pv_list))


def make_default_pv_list():
    return [
        make_default_pv(),
        make_second_pv()
    ]


def make_default_pv():
    return ProcessVariable(FIRST_PV_NAME, FIRST_PV_DEFAULT_VALUE)


def make_second_pv():
    return ProcessVariable(SECOND_PV_NAME, SECOND_PV_DEFAULT_VALUE)
