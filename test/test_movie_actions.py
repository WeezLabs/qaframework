import allure
import pytest

import constants
from main.data.movie_data import MovieData
from main.data.user_data import UserData
from main.services.movie_api import MovieAPI
from main.services.user_api import UserAPI
from main.utils.assertation import is_equal, is_not_empty, is_empty


def send_test_data():
    return ["python"]


@allure.feature("Distributor Dashboard Testing")
@allure.story("Distributor Dashboard Main Page Content Testing")
class TestUserAction:

    @allure.title("Verify success create movie")
    @allure.severity(allure.severity_level.NORMAL)
    def test_success_create_movie(self, create_services):
        movie_api = create_services
        status_code, movie_data = movie_api.create_movie()
        is_equal(status_code, 200)
        pytest.movie_id = movie_data["id"]
        is_not_empty(pytest.movie_id)

    @allure.title("Verify creating movie with out user login")
    @allure.severity(allure.severity_level.NORMAL)
    def test_create_movie_without_login(self):
        movie = MovieAPI({})
        status_code, movie_data = movie.create_movie()
        is_equal(status_code, 403)
        is_empty(movie_data.get("id"))

    @allure.title("Verify creating movie twice by one user")
    @allure.severity(allure.severity_level.NORMAL)
    def test_creating_movie_twice_by_one_user(self, create_movie_by_user):
        _, movie, movie_creating_data = create_movie_by_user

        status_code, movie_data = movie.create_movie(movie_creating_data[constants.Indexes.START_INDEX])
        is_equal(status_code, 400)
        is_empty(movie_data.get("id"))

    @allure.title("Verify creating movie twice by different user")
    @allure.severity(allure.severity_level.NORMAL)
    def test_creating_movie_twice_by_different_user(self, create_user_with_movie, create_services):
        movie_creating_data, _ = create_user_with_movie
        movie_service = create_services
        status_code, movie_data = movie_service.create_movie(movie_creating_data[constants.Indexes.START_INDEX])
        is_equal(status_code, 400)
        is_empty(movie_data.get("id"))

    @allure.title("Verify unsuccess create movie")
    @allure.severity(allure.severity_level.NORMAL)
    def test_unsuccess_create_movie(self, unsuccess_movie_data_provider, create_services):
        name = unsuccess_movie_data_provider[constants.Indexes.MOVIE_NAME_INDEX]
        genres = unsuccess_movie_data_provider[constants.Indexes.GENRES_INDEX]
        casts = unsuccess_movie_data_provider[constants.Indexes.CASTS_INDEX]
        movie = MovieData()
        movie.builder(name, genres, casts)
        status_code, movie_data = create_services.create_movie(movie)
        is_equal(status_code, 400)
        is_empty(movie_data.get("id"))

    @allure.title("Verify successfully update movie")
    @allure.severity(allure.severity_level.NORMAL)
    def test_updating_movie(self, create_movie_by_user):
        movie_ids, movie, _ = create_movie_by_user

        movie_updating_data = MovieData()
        movie_id = movie_ids[constants.Indexes.START_INDEX][constants.Indexes.ID_INDEX].get("id")
        status_code, movie_data = movie.update_movie(movie_id, movie_updating_data)
        is_equal(status_code, 200)
        status_code, movie_actual_data = movie.get_movie(movie_id)
        is_equal(movie_actual_data["casts"], movie_updating_data.get_casts())
        is_equal(movie_actual_data["genres"], movie_updating_data.get_genres())
        is_equal(movie_actual_data["name"], movie_updating_data.get_name())

    @allure.title("Verify unsuccessfully update movie creation by another user")
    @allure.severity(allure.severity_level.NORMAL)
    def test_updating_another_user_movie(self, create_user_with_movie, create_services):
        _, movie_ids = create_user_with_movie
        movie = create_services

        movie_updating_data = MovieData()
        movie_id = movie_ids[constants.Indexes.START_INDEX][constants.Indexes.ID_INDEX].get("id")
        status_code, movie_data = movie.update_movie(movie_id, movie_updating_data)
        is_equal(status_code, 400)

    @allure.title("Verify unsuccessfully update movie creation by current user")
    @allure.severity(allure.severity_level.NORMAL)
    def test_updating_current_user_movie(self, create_user_with_movie, create_movie_by_user):
        movie_creation_data, _ = create_user_with_movie
        movie_ids, movie_api, _ = create_movie_by_user

        movie_id = movie_ids[constants.Indexes.START_INDEX][constants.Indexes.ID_INDEX].get("id")
        status_code, movie_data = movie_api.update_movie(movie_id, movie_creation_data[constants.Indexes.START_INDEX])
        is_equal(status_code, 400)

    @allure.title("Verify unsuccessfully update movie by user")
    @allure.severity(allure.severity_level.NORMAL)
    @pytest.mark.MOVIE_COUNT(2)
    def test_updating_current_user_movie_twice(self, create_movie_by_user):
        movie_ids, movie_api, movie_creation_data = create_movie_by_user

        movie_id = movie_ids[constants.Indexes.LAST_INDEX][constants.Indexes.ID_INDEX].get("id")
        movie_new_data = movie_creation_data[constants.Indexes.START_INDEX]
        status_code, movie_data = movie_api.update_movie(movie_id, movie_new_data)
        is_equal(status_code, 400)

    @allure.title("Verify unsuccessfully update movie")
    @allure.severity(allure.severity_level.NORMAL)
    def test_negative_updating_movie(self, create_movie_by_user, get_movie_data):
        movie_ids, movie_api, movie_creation_data = create_movie_by_user
        movie = get_movie_data
        movie_id = movie_ids[constants.Indexes.START_INDEX][constants.Indexes.ID_INDEX].get("id")
        status_code, movie_data = movie_api.update_movie(movie_id, movie)
        is_equal(status_code, 400)

