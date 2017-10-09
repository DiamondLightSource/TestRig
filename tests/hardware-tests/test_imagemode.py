from malcolmtest import MalcolmTestCase


class TestImageMode(MalcolmTestCase):
    def test_cannot_set_image_mode_to_empty_string(self):
        self.assert_set_image_mode_raises_malcolm_response_error("")

    def test_cannot_set_image_mode_to_invalid_string(self):
        self.assert_set_image_mode_raises_malcolm_response_error("a")

    def test_can_set_image_mode_to_fixed(self):
        self.assert_set_image_mode_sets_image_mode("Fixed")

    def test_can_set_image_mode_to_continuous(self):
        self.assert_set_image_mode_sets_image_mode("Continuous")

    def assert_set_image_mode_raises_malcolm_response_error(self, demand_value):
        image_mode = self._camera.imageMode
        self.assert_set_attribute_raises_malcolm_response_error(image_mode, demand_value)

    def assert_set_image_mode_sets_image_mode(self, expected_readback_value, demand_value=None):
        image_mode = self._camera.imageMode
        self.assert_set_attribute_sets_attribute(image_mode, expected_readback_value, demand_value)
