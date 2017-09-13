import unittest
from test_areadetector import AreaDetectorTestCase


class AndorDetectorTestCase(AreaDetectorTestCase):
    ANDOR_DEFAULTS_SAVE = "ANDOR-DEFAULTS"

    __test__ = True

    @classmethod
    def set_up_blocks(cls):
        cls._malcolm.load_yaml("test-yaml-configs/bl99p-ea-det-01.yaml")
        cls._detector = cls._malcolm.block_view("ANDOR")
        cls._camera = cls._malcolm.block_view("ANDOR:DRV")

    @classmethod
    def save_state(cls):
        cls._detector.save(cls.ANDOR_DEFAULTS_SAVE)

    @classmethod
    def restore_state(cls):
        cls._detector.design.put_value(cls.ANDOR_DEFAULTS_SAVE)
