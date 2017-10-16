class TestImageAcquire:
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
        self._camera.imageMode.put_value("Fixed")
        self._camera.numImages.put_value(demand_number_of_images)
        self.assert_acquires_number_of_frames(expected_number_of_images)

    def assert_acquires_number_of_frames(self, expected_number_of_frames):
        acquire_period = self._camera.acquirePeriod.value
        expected_time_to_take_frames = acquire_period * expected_number_of_frames

        self._camera.start(timeout=expected_time_to_take_frames * 2)
        self._camera.stop()
        self.assert_array_counter_equals(expected_number_of_frames)

    def assert_array_counter_equals(self, expected_value):
        self.assertEqual(expected_value, self._camera.arrayCounterReadback.value)
