from pages.main_page import MainPage
import pytest


@pytest.mark.login_guest
class TestLoginFromMainPage:
    def test_main_page_header_is_correct(self, driver):
        main_page = MainPage(driver)
        main_page.open()

        actual_header_text = main_page.get_element(main_page.header_locator).text
        expected_header_text = main_page.main_page_header_text

        assert actual_header_text == expected_header_text, \
            "Expected is :{}, but actual was :{}".format(expected_header_text, actual_header_text)

    def test_learn_about_us_button_is_displayed(self, driver):
        main_page = MainPage(driver)
        main_page.open()

        learn_about_us_button = main_page.get_element(main_page.learn_about_us_button)

        assert learn_about_us_button.is_displayed()
