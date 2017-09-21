import unittest

from malcolmtest import MalcolmTestCase, make_block_factory_from_connection

TIMEOUT_A_FEW_FRAMES = 5
ANDOR_DEFAULTS_SAVE = "ANDOR-DEFAULTS"


class AndorDetectorTestCase(MalcolmTestCase):
    @classmethod
    def set_up_blocks(cls):
        block_factory = make_block_factory_from_connection(cls._malcolm)
        block_factory.load_yaml("config/hardware-control-config/bl99p-ea-det-01.yaml")
        cls._detector = block_factory.block_view("ANDOR")
        cls._camera = block_factory.block_view("ANDOR:DRV")

    @classmethod
    def save_state(cls):
        cls._detector.save(ANDOR_DEFAULTS_SAVE)

    @classmethod
    def restore_state(cls):
        # arrayCounter is a non-standard value as it is changed by the camera and not the user.
        # It's not treated as configurable by malcolm, so it isn't saved like other variables.
        # Jira: http://jira.diamond.ac.uk/browse/BC-505
        cls._camera.arrayCounter.put_value(0)
        cls._detector.design.put_value(ANDOR_DEFAULTS_SAVE)

    def test_set_exposure_to_zero(self):
        self.assert_set_exposure_sets_exposure(0, 0)

    def test_set_exposure_to_positive(self):
        self.assert_set_exposure_sets_exposure(1, 1)

    def test_set_exposure_to_negative(self):
        self.assert_set_exposure_sets_exposure(0, -1)

    @unittest.skip(
        "Skipping this due to a known bug. Jira:http://jira.diamond.ac.uk/browse/P99-7?jql=project%20%3D%20P99")
    def test_set_acquire_period_to_zero(self):
        self.assert_set_exposure_sets_exposure(0.5, 0.5)
        self.assert_set_acquire_period_sets_acquire_period(0.5, 0)

    @unittest.skip(
        "Skipping this due to a known bug. Jira:http://jira.diamond.ac.uk/browse/P99-7?jql=project%20%3D%20P99")
    def test_set_acquire_period_to_negative(self):
        self.assert_set_exposure_sets_exposure(0.5, 0.5)
        self.assert_set_acquire_period_sets_acquire_period(0.5, -0.5)

    def test_set_acquire_period_greater_than_exposure_limit(self):
        self.assert_set_exposure_sets_exposure(0.5, 0.5)
        self.assert_set_acquire_period_sets_acquire_period(0.51, 0.6)

    def test_set_acquire_period_within_exposure_limit(self):
        self.assert_set_exposure_sets_exposure(0.5, 0.5)
        self.assert_set_acquire_period_sets_acquire_period(0.501, 0.501)

    def test_set_acquire_period_less_than_exposure(self):
        self.assert_set_exposure_sets_exposure(0.5, 0.5)
        self.assert_set_acquire_period_sets_acquire_period(0.5, 0.4)

    def test_set_num_images_less_than_zero(self):
        self.assert_set_num_images_sets_num_images(-1, -1)

    def test_set_num_images_to_zero(self):
        self.assert_set_num_images_sets_num_images(0, 0)

    def test_set_num_images_to_more_than_zero(self):
        self.assert_set_num_images_sets_num_images(1, 1)

    def test_acquire_zero_images(self):
        self.assert_set_num_images_sets_num_images(0, 0)
        self.acquire_n_frames(1, TIMEOUT_A_FEW_FRAMES)

    def test_acquire_negative_images(self):
        self.assert_set_num_images_sets_num_images(-1, -1)
        self.acquire_n_frames(1, TIMEOUT_A_FEW_FRAMES)

    def test_acquire_one_image(self):
        self.assert_set_num_images_sets_num_images(1, 1)
        self.acquire_n_frames(1, TIMEOUT_A_FEW_FRAMES)

    def test_acquire_multiple_images(self):
        self.assert_set_num_images_sets_num_images(2, 2)
        self.acquire_n_frames(2, TIMEOUT_A_FEW_FRAMES)

    def acquire_n_frames(self, expected_num_frames, timeout):
        self._camera.start()
        self.assert_array_counter_reaches(expected_num_frames, timeout)
        self._camera.stop()

    def assert_set_exposure_sets_exposure(self, expected_readback_value, demand_value):
        exposure = self._camera.exposure
        self.assert_set_attribute_sets_attribute(exposure, demand_value, expected_readback_value)

    def assert_set_acquire_period_sets_acquire_period(self, expected_readback_value, demand_value):
        acquire_period = self._camera.acquirePeriod
        self.assert_set_attribute_sets_attribute(acquire_period, demand_value, expected_readback_value)

    def assert_set_num_images_sets_num_images(self, expected_readback_value, demand_value):
        num_images = self._camera.numImages
        self.assert_set_attribute_sets_attribute(num_images, demand_value, expected_readback_value)

    def assert_set_attribute_sets_attribute(self, attribute, demand_value, expected_readback_value):
        attribute.put_value(demand_value)
        self.assertAlmostEqual(expected_readback_value, attribute.value, 3)

    def assert_array_counter_reaches(self, expected_num_frames, timeout):
        self._camera.when_value_matches("arrayCounter", expected_num_frames, timeout=timeout)
        self.assert_array_counter(expected_num_frames)

    def assert_array_counter(self, expected):
        self.assertEqual(expected, self._camera.arrayCounter.value)
