from main.core.baseApp import BasePage
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys


class DuckDuckGoSearchPage:
    locator_search_input = (By.ID, 'search_form_input_homepage')


class SearchHelper(BasePage):

    def search(self, phrase):
        search_input = self.find_element(DuckDuckGoSearchPage.locator_search_input)
        search_input.send_keys(phrase + Keys.RETURN)
        return search_input
