from unittest import TestCase

import pytest

from testutils.malcolm.test_andordetector import AndorDetectorProcessTestCase


class TestBl99PEaDet01(AndorDetectorProcessTestCase, TestCase):
    @classmethod
    def get_yaml_path(cls):
        return "config/hardware-control-config/bl99p-ea-det-01.yaml"

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
