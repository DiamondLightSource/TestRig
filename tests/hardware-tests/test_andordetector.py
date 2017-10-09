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

    def test_acquire_zero_images(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(1, 0)

    def test_acquire_negative_number_of_images(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(1, -1)

    def test_acquire_single_image(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(1)

    def test_acquire_multiple_images_in_fixed_mode(self):
        self.assert_acquires_number_of_frames_in_fixed_image_mode(2)

    def assert_acquires_number_of_frames_in_fixed_image_mode(self, expected_number_of_images,
                                                             demand_number_of_images=None):
        if not demand_number_of_images:
            demand_number_of_images = expected_number_of_images
        self.assert_set_image_mode_sets_image_mode("Fixed")
        self.assert_set_num_images_sets_num_images(demand_number_of_images)
        self.assert_acquires_number_of_frames(expected_number_of_images)

    def assert_acquires_number_of_frames(self, expected_num_frames, timeout=DEFAULT_CAPTURE_TIMEOUT_SECONDS):
        self._camera.start()
        self.assert_array_counter_reaches(expected_num_frames, timeout)
        self._camera.stop()

    def assert_array_counter_reaches(self, expected_num_frames, timeout=DEFAULT_CAPTURE_TIMEOUT_SECONDS):
        self._camera.when_value_matches("arrayCounter", expected_num_frames, timeout=timeout)
        self.assert_array_counter_equals(expected_num_frames)

    def assert_array_counter_equals(self, expected_value):
        self.assertEqual(expected_value, self._camera.arrayCounter.value)
