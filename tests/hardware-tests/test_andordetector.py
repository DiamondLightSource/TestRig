import unittest

from malcolmtest import MalcolmTestCase, make_block_factory_from_connection

DEFAULT_CAPTURE_TIMEOUT_SECONDS = 300
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
        self.assert_set_exposure_sets_exposure(0)

    def test_set_exposure_to_positive_value(self):
        self.assert_set_exposure_sets_exposure(1)

    def test_set_exposure_to_negative_value(self):
        self.assert_set_exposure_sets_exposure(0, -1)

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

    def test_set_num_images_to_zero(self):
        self.assert_set_num_images_sets_num_images(0)

    def test_set_num_images_to_positive_value(self):
        self.assert_set_num_images_sets_num_images(1)

    def test_set_num_images_to_negative_value(self):
        self.assert_set_num_images_sets_num_images(-1)

    def test_cannot_set_image_mode_to_empty_string(self):
        self.assert_set_image_mode_raises_malcolm_response_error("")

    def test_cannot_set_image_mode_to_invalid_string(self):
        self.assert_set_image_mode_raises_malcolm_response_error("a")

    def test_can_set_image_mode_to_fixed(self):
        self.assert_set_image_mode_sets_image_mode("Fixed")

    def test_can_set_image_mode_to_continuous(self):
        self.assert_set_image_mode_sets_image_mode("Continuous")

    def test_acquire_zero_images(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(1, 0)

    def test_acquire_negative_number_of_images(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(1, -1)

    def test_acquire_single_image(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(1, 1)

    def test_acquire_multiple_images_in_fixed_mode(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(2, 2)

    def assert_set_image_mode_sets_image_mode(self, expected_readback_value, demand_value=None):
        image_mode = self._camera.imageMode
        self.assert_set_attribute_sets_attribute(image_mode, expected_readback_value, demand_value)

    def assert_acquires_number_of_frames_in_fixed_image_mode(self, expected_number_of_images, demand_number_of_images):
        # Set image mode to fixed here!
        self.assert_set_num_images_sets_num_images(demand_number_of_images)
        self.assert_acquires_number_of_frames(expected_number_of_images)

    def assert_acquires_number_of_frames(self, expected_num_frames, timeout=DEFAULT_CAPTURE_TIMEOUT_SECONDS):
        self._camera.start()
        self.assert_array_counter_reaches(expected_num_frames, timeout)
        self._camera.stop()

    def assert_set_exposure_sets_exposure(self, expected_readback_value, demand_value=None):
        exposure = self._camera.exposure
        self.assert_set_attribute_sets_attribute(exposure, expected_readback_value, demand_value)

    def assert_set_exposure_sets_acquire_period(self, expected_acquire_period_readback_value,
                                                exposure_demand_value):
        exposure = self._camera.exposure
        acquire_period = self._camera.acquirePeriod
        self.assert_set_attribute_sets_attribute(exposure, expected_acquire_period_readback_value,
                                                 exposure_demand_value, acquire_period)

    def assert_set_acquire_period_sets_acquire_period(self, expected_readback_value, demand_value=None):
        acquire_period = self._camera.acquirePeriod
        self.assert_set_attribute_sets_attribute(acquire_period, expected_readback_value, demand_value)

    def assert_set_num_images_sets_num_images(self, expected_readback_value, demand_value=None):
        num_images = self._camera.numImages
        self.assert_set_attribute_sets_attribute(num_images, expected_readback_value, demand_value)

    def assert_set_attribute_sets_attribute(self, attribute, expected_readback_value, demand_value=None,
                                            attribute_to_read=None):
        if demand_value is None:
            demand_value = expected_readback_value
        if attribute_to_read is None:
            attribute_to_read = attribute
        attribute.put_value(demand_value)
        self.assertAlmostEqual(expected_readback_value, attribute_to_read.value, 2)

    def assert_set_image_mode_raises_malcolm_response_error(self, demand_value):
        image_mode = self._camera.imageMode
        self.assert_set_attribute_raises_malcolm_response_error(image_mode, demand_value)

    def assert_set_attribute_raises_malcolm_response_error(self, attribute, demand_value):
        from malcolm.core import ResponseError
        self.assert_set_attribute_raises_error(attribute, ResponseError, demand_value)

    def assert_set_attribute_raises_error(self, attribute, expected_error_type, demand_value):
        with self.assertRaises(expected_error_type):
            attribute.put_value(demand_value)

    def assert_array_counter_reaches(self, expected_num_frames, timeout=DEFAULT_CAPTURE_TIMEOUT_SECONDS):
        self._camera.when_value_matches("arrayCounter", expected_num_frames, timeout=timeout)
        self.assert_array_counter_equals(expected_num_frames)

    def assert_array_counter_equals(self, expected_value):
        self.assertEqual(expected_value, self._camera.arrayCounter.value)
