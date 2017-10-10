import pytest

from unittest import TestCase
from testutils.hardware.areadetector.test_andordetector import AndorDetectorTestCase
from testutils.hardware.malcolmtest import MalcolmTestCase

class TestBl99PEaDet01(AndorDetectorTestCase, TestCase):
    @pytest.mark.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_acquire_period_follows_exposure(self):
        pass

    @pytest.mark.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_acquire_period_does_not_follow_exposure_below_minimum_value(self):
        pass

    @pytest.mark.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_acquire_period_does_not_follow_exposure_to_zero(self):
        pass

    @pytest.mark.skip("Skipping due to a known bug, Jira: http://jira.diamond.ac.uk/browse/P99-6")
    def test_can_manually_set_acquire_period_away_from_exposure(self):
        pass
