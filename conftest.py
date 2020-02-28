import os
import shutil

import allure
import jsonpickle
import pytest

import constants
import settings
from main.data.movie_data import MovieData
from main.helpers import services_helper
from main.services import user_api
from main.services.movie_api import MovieAPI
from main.services.user_api import UserAPI
from main.utils.data_providers import generate_test_data_provider

pytest.movie_id = constants.DefaultValues.ZERO
pytest.movies = 1


def pytest_addoption(parser):
    parser.addoption("--api_host_url", action="store", default="http://127.0.0.1:3500/api")
    parser.addoption("--username", action="store", default="mberesneva@distillery.com")
    parser.addoption("--password", action="store", default="password")


@pytest.hookimpl(hookwrapper=True, tryfirst=True)
def pytest_runtest_makereport(item):
    outcome = yield
    rep = outcome.get_result()
    setattr(item, "rep_" + rep.when, rep)
    return rep


@pytest.fixture()
def config(request):
    settings.API_HOST_URL = request.config.getoption("--api_host_url")
    username = request.config.getoption("--username")
    password = request.config.getoption("--password")
    return {"username": username,
            "password": password
            }


def pytest_configure(config):
    config.addinivalue_line("markers", "MOVIE_COUNT(value): count of movies is needed for test")


@pytest.fixture()
def create_user(request):
    user_services, user_data = services_helper.create_user()

    def delete_user():
        services_helper.delete_user(user_services)

    request.addfinalizer(delete_user)
    yield user_services, user_data


@pytest.fixture()
def login_user(create_user):
    user_services, user_data = create_user
    user_services.login_user(user_data.username, user_data.password)
    yield user_services, user_data


@pytest.fixture()
def login_specify_user(config):
    user_services = UserAPI()
    user_services.login_user(config["username"], config["password"])
    yield user_services


@pytest.fixture()
def create_specify_user(request, config):
    user_services, user_data = services_helper.create_user(config["username"], config["password"])

    def delete_user():
        services_helper.delete_user(user_services)

    request.addfinalizer(delete_user)
    yield user_services, user_data


@pytest.fixture()
def create_services(request, login_specify_user):
    user_services = login_specify_user
    movie = MovieAPI(user_services.get_token_header())

    def clean_services():
        if pytest.movie_id != "0":
            movie.delete_movie(pytest.movie_id)

    request.addfinalizer(clean_services)
    yield movie


@pytest.fixture()
def init_user(request):
    user = UserAPI()

    def delete_user():
        user.delete_user()

    request.addfinalizer(delete_user)
    yield user


@pytest.fixture()
def create_user_with_movie(request, login_user):
    user_services, user_data = login_user
    movie_api, movie_ids, movie = services_helper.create_movie_by_user(user_services)

    def delete_movie():
        for movie_id in movie_ids:
            movie_api.delete_movie(movie_id[1].get("id"))

    request.addfinalizer(delete_movie)
    yield movie, movie_ids


@pytest.fixture()
def create_movie_by_user(request, login_specify_user):
    user_services = login_specify_user
    movie_api, movie_ids, movie = services_helper.create_movie_by_user(user_services, pytest.movies)

    def delete_movie():
        for movie_id in movie_ids:
            movie_api.delete_movie(movie_id[1].get("id"))

    request.addfinalizer(delete_movie)
    yield movie_ids, movie_api, movie


@pytest.fixture()
def get_movie_data(unsuccess_movie_data_provider):
    movie_name = unsuccess_movie_data_provider[constants.Indexes.MOVIE_NAME_INDEX]
    genres = unsuccess_movie_data_provider[constants.Indexes.GENRES_INDEX]
    casts = unsuccess_movie_data_provider[constants.Indexes.CASTS_INDEX]
    movie = MovieData()
    movie.builder(movie_name, genres, casts)
    yield movie


def pytest_runtest_setup(item):
    is_needed = [mark.args[0] for mark in item.iter_markers(name="MOVIE_COUNT")]
    if len(is_needed) > 0:
        pytest.movies = is_needed[0]


def pytest_generate_tests(metafunc):
    generate_test_data_provider(metafunc)
