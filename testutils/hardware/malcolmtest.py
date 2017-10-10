import unittest

from malcolm.core import Process, call_with_params
from malcolm.yamlutil import make_include_creator


def make_block_factory_from_connection(malcolm_connection):
    return MalcolmBlockFactory(malcolm_connection.get_process())

class MalcolmDeviceTestCase:
    def assert_set_attribute_sets_attribute(self, attribute, expected_readback_value, demand_value=None,
                                            attribute_to_read=None):
        if not demand_value:
            demand_value = expected_readback_value
        if not attribute_to_read:
            attribute_to_read = attribute
        attribute.put_value(demand_value)
        self.assertAlmostEqual(expected_readback_value, attribute_to_read.value, 2)

    def assert_set_attribute_raises_malcolm_response_error(self, attribute, demand_value):
        from malcolm.core import ResponseError
        self.assert_set_attribute_raises_error(attribute, ResponseError, demand_value)

    def assert_set_attribute_raises_error(self, attribute, expected_error_type, demand_value):
        with self.assertRaises(expected_error_type):
            attribute.put_value(demand_value)


class MalcolmTestCase:
    _process = None

    @classmethod
    def setUpClass(cls):
        cls._process = Process("Test Case")
        cls._process.start()
        yaml_path = cls.get_yaml_path()
        call_with_params(make_include_creator(yaml_path), cls._process)

    @classmethod
    def tearDownClass(cls):
        cls._process.stop()

    @classmethod
    def get_yaml_path(cls):
        pass

    def setUp(self):
        self.setup_blocks(self._process)
        self.restore_state()
