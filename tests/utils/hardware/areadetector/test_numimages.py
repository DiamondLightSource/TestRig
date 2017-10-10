class TestNumImages:
    def test_set_num_images_to_zero(self):
        self.assert_set_num_images_sets_num_images(0)

    def test_set_num_images_to_positive_value(self):
        self.assert_set_num_images_sets_num_images(1)

    def test_set_num_images_to_negative_value(self):
        self.assert_set_num_images_sets_num_images(-1)

    def assert_set_num_images_sets_num_images(self, expected_readback_value, demand_value=None):
        num_images = self._camera.numImages
        self.assert_set_attribute_sets_attribute(num_images, expected_readback_value, demand_value)
