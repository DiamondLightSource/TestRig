import unittest
from malcolmtest import MalcolmTestCase


class AreaDetectorTestCase(MalcolmTestCase):
    _detector = None
    _camera = None
    __test__ = False

    def test_set_exposure_to_zero(self):
        self.do_set_exposure_check(0)

    def test_set_exposure_to_positive(self):
        self.do_set_exposure_check(0.1)

    def test_set_exposure_to_negative(self):
        self.do_set_exposure(0, -0.1)

    @unittest.skip(
        "Skipping this due to a known bug. Jira:http://jira.diamond.ac.uk/browse/P99-7?jql=project%20%3D%20P99")
    def test_set_acquisition_period_to_zero(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.5, 0)

    @unittest.skip(
        "Skipping this due to a known bug. Jira:http://jira.diamond.ac.uk/browse/P99-7?jql=project%20%3D%20P99")
    def test_set_acquisition_period_to_negative(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.5, -0.5)

    def test_set_acquisition_period_greater_than_exposure_limit(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.51, 0.6)

    def test_set_acquisition_period_within_exposure_limit(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.501, 0.501)

    def test_set_acquisition_period_less_than_exposure(self):
        self.do_set_acquisition_period_and_exposure(0.5, 0.5, 0.4)

    def test_set_num_images_less_than_zero(self):
        self.do_set_num_images(-1, -1)

    def test_set_num_images_to_zero(self):
        self.do_set_num_images(0, 0)

    def do_set_num_images(self, expected, actual):
        self._camera.numImages.put_value(actual)
        self.assertEqual(expected, self._camera.numImages.value)

    def do_set_acquisition_period_and_exposure(self, exposure, expected_acquisition_period, actual_acquisition_period):
        self.do_set_exposure_check(exposure)
        self.do_set_acquisition_period(expected_acquisition_period, actual_acquisition_period)

    def do_set_acquisition_period(self, expected, actual):
        self._camera.acquirePeriod.put_value(actual)
        self.assert_acquisition_period(expected)

    def do_set_exposure_check(self, value):
        self.do_set_exposure(value, value)

    def do_set_exposure(self, expected, value):
        self._camera.exposure.put_value(value)
        self.assert_exposure(expected)

    def assert_acquisition_period(self, expected):
        self.assert_almost_equal(expected, self._camera.acquirePeriod.value)

    def assert_exposure(self, expected):
        self.assert_almost_equal(expected, self._camera.exposure.value)
