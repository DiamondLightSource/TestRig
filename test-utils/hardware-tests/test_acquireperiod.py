from _pytest import unittest

from malcolmtest import MalcolmTestCase


class TestAcquirePeriod(MalcolmTestCase):
    @unittest.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_acquire_period_follows_exposure(self):
        self.assert_set_exposure_sets_acquire_period(0.5, 0.5)

    @unittest.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_acquire_period_does_not_follow_exposure_below_minimum_value(self):
        self.assert_set_exposure_sets_acquire_period(0.01, 0.009)

    @unittest.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_acquire_period_does_not_follow_exposure_to_zero(self):
        self.assert_set_exposure_sets_acquire_period(0.01, 0)

    @unittest.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_can_manually_set_acquire_period_away_from_exposure(self):
        self._camera.exposure.put_value(0.01)
        self.assert_set_acquire_period_sets_acquire_period(0.5)

    def assert_set_exposure_sets_acquire_period(self, expected_acquire_period_readback_value,
                                                exposure_demand_value):
        exposure = self._camera.exposure
        acquire_period = self._camera.acquirePeriod
        self.assert_set_attribute_sets_attribute(exposure, expected_acquire_period_readback_value,
                                                 exposure_demand_value, acquire_period)

    def assert_set_acquire_period_sets_acquire_period(self, expected_readback_value, demand_value=None):
        acquire_period = self._camera.acquirePeriod
        self.assert_set_attribute_sets_attribute(acquire_period, expected_readback_value, demand_value)