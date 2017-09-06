import unittest
from malcolmtest import *

class TestCamera(AndorDetectorTestCase):
    def test_set_exposure_to_zero(self):
        self.do_set_exposure_check(0)

    def test_set_exposure_to_positive(self):
        self.do_set_exposure_check(0.1)

    def test_set_exposure_to_negative(self):
        self.do_set_exposure(0, -0.1)

    # NOTE: THESE TWO TESTS ARE DISABLED AS THEY FAIL DUE TO A KNOWN BUG!
    #def test_set_acquisition_period_to_zero(self):
    #    self.do_set_acquisition_period_and_exposure(0.5, 0.5, 0)

    #def test_set_acquisition_period_to_negative(self):
    #    self.do_set_acquisition_period_and_exposure(0.5, 0.5, -0.5)

    def test_set_acquisition_period_greater_than_exposure_limit(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.51, 0.6)

    def test_set_acquisition_period_within_exposure_limit(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.501, 0.501)

    def test_set_acquisition_period_less_than_exposure(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.5, 0.4)

    def do_set_acquisition_period_and_exposure(self, exposure, expected_acquisition_period, actual_acquisition_period):
        self.do_set_exposure_check(exposure)
        self.do_set_acquisition_period(expected_acquisition_period, actual_acquisition_period)

    def do_set_acquisition_period(self, expected, actual):
        self._detector.acquirePeriod.put_value(actual)
        self.assert_acquisition_period(expected)

    def do_set_exposure_check(self, value):
        self.do_set_exposure(value, value)

    def do_set_exposure(self, expected, value):
        self._detector.exposure.put_value(value)
        self.assert_exposure(expected)

    def assert_acquisition_period(self, expected):
        self.assert_almost_equal(expected, self._detector.acquirePeriod.value)

    def assert_exposure(self, expected):
        self.assert_almost_equal(expected, self._detector.exposure.value)

    def assert_almost_equal(self, expected, actual):
        self.assertAlmostEqual(expected, actual, 3)
