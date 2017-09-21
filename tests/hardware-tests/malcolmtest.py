import unittest
from malcolm.core import Process, call_with_params
from malcolm.yamlutil import make_include_creator


def make_block_factory_from_connection(malcolm_connection):
    return MalcolmBlockFactory(malcolm_connection.get_process())


class MalcolmConnection:
    def __init__(self, name):
        self._process = Process(name)

    def open(self):
        self._process.start()

    def close(self):
        self._process.stop()

    def get_process(self):
        return self._process


class MalcolmBlockFactory:
    def __init__(self, process):
        self._process = process

    def load_yaml(self, yaml_path):
        call_with_params(make_include_creator(yaml_path), self._process)

    def block_view(self, block_mri):
        return self._process.block_view(block_mri)


class MalcolmTestCase(unittest.TestCase):
    _malcolm = None

    @classmethod
    def setUpClass(cls):
        cls._malcolm = MalcolmConnection("Test Case")
        cls._malcolm.open()
        cls.set_up_blocks()
        cls.save_state()

    @classmethod
    def tearDownClass(cls):
        cls._malcolm.close()

    @classmethod
    def set_up_blocks(cls):
        pass

    @classmethod
    def save_state(cls):
        pass

    @classmethod
    def restore_state(cls):
        pass

    def setUp(self):
        self.restore_state()
