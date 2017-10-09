from malcolmtest import MalcolmTestCase

class TestExposure(MalcolmTestCase):
    def test_set_exposure_to_zero(self):
        self.assert_set_exposure_sets_exposure(0)

    def test_set_exposure_to_positive_value(self):
        self.assert_set_exposure_sets_exposure(1)

    def test_set_exposure_to_negative_value(self):
        self.assert_set_exposure_sets_exposure(0, -1)

    def assert_set_exposure_sets_exposure(self, expected_readback_value, demand_value=None):
        exposure = self._camera.exposure
        self.assert_set_attribute_sets_attribute(exposure, expected_readback_value, demand_value)