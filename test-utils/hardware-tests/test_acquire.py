from malcolmtest import MalcolmTestCase

DEFAULT_CAPTURE_TIMEOUT_SECONDS = 300

class TestAcquire(MalcolmTestCase):
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
        self._camera.ImageMode.put_value("Fixed")
        self._camera.NumImages.put_value(demand_number_of_images)
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