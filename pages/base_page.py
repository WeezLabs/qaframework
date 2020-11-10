from selenium.common.exceptions import NoSuchElementException


class BasePage:

    def __init__(self, driver):
        self.driver = driver

    def navigate_to(self, url):
        self.driver.get(url)

    def get_element(self, locator: tuple, ):
        return self.driver.find_element(*locator)

    def get_script(self, locator):
        try:
            self.get_element(locator)
        except NoSuchElementException:
            return False
        return True
