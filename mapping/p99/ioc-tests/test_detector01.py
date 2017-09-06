import unittest
from malcolmtest import *

class TestCamera(AndorDetectorTestCase):
    def test_set_exposure_to_zero(self):
        self.do_set_exposure(0, 0)

    def test_set_exposure_to_positive(self):
        self.do_set_exposure(0.1, 0.1)

    def test_set_exposure_to_negative(self):
        self.do_set_exposure(0, -0.1)

    #def test_set_acquisition_period_greater_than_exposure(self):
    #    self.do_set_exposure(0.5, 0.5)
    #    self._detector.acquirePeriod.put_value(0.6)
    #    self.assert_acquisition_period(0.6)

    def do_set_exposure(self, expected, value):
        self._detector.exposure.put_value(value)
        self.assert_exposure(expected)

    def assert_acquisition_period(self, expected):
        self.assert_almost_equal(expected, self._detector.acquirePeriod.value)

    def assert_exposure(self, expected):
        self.assert_almost_equal(expected, self._detector.exposure.value)

    def assert_almost_equal(self, expected, actual):
        self.assertAlmostEqual(expected, actual, 4)
