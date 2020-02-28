import allure
import pytest

import constants
from main.data.user_data import UserData
from main.services.user_api import UserAPI
from main.utils.assertation import is_equal, is_not_empty, is_empty


def send_test_data():
    return ["python"]


@allure.feature("Distributor Dashboard Testing")
@allure.story("Distributor Dashboard Main Page Content Testing")
class TestUserAction:

    @allure.title("Verify user success login")
    @allure.severity(allure.severity_level.NORMAL)
    def test_user_success_login(self, success_login_data_provider):
        username = success_login_data_provider[constants.Indexes.USERNAME_INDEX]
        password = success_login_data_provider[constants.Indexes.PASSWORD_INDEX]
        status_code, user_data = UserAPI().login_user(username, password)
        is_equal(status_code, 200)
        is_not_empty(user_data["id"])

    @allure.title("Verify user success create user")
    @allure.severity(allure.severity_level.NORMAL)
    def test_user_success_create(self, init_user, success_create_user_data_provider):
        username = success_create_user_data_provider[constants.Indexes.USERNAME_INDEX]
        password = success_create_user_data_provider[constants.Indexes.PASSWORD_INDEX]
        status_code, user_data = init_user.sign_up_user(username, password)
        is_equal(status_code, 200)
        is_not_empty(user_data["id"])
        status_code, user_data = init_user.login_user(username, password)
        is_equal(status_code, 200)

    @allure.title("Verify user unsuccess login")
    @allure.severity(allure.severity_level.NORMAL)
    def test_user_unsuccess_login(self, unsuccess_login_data_provider):
        username = unsuccess_login_data_provider[constants.Indexes.USERNAME_INDEX]
        password = unsuccess_login_data_provider[constants.Indexes.PASSWORD_INDEX]
        status_code, user_data = UserAPI().login_user(username, password)
        is_equal(status_code, 401)
        is_empty(user_data.get("id"))

    @allure.title("Verify user unsuccess create user")
    @allure.severity(allure.severity_level.NORMAL)
    def test_user_unsuccess_create_user(self, unsuccess_create_user_data_provider):
        username = unsuccess_create_user_data_provider[constants.Indexes.USERNAME_INDEX]
        password = unsuccess_create_user_data_provider[constants.Indexes.PASSWORD_INDEX]
        status_code, user_data = UserAPI().sign_up_user(username, password)
        is_equal(status_code, 400)
        is_empty(user_data.get("id"))
