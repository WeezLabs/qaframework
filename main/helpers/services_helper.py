import constants
from main.data.movie_data import MovieData
from main.data.user_data import UserData
from main.domain import primitives
from main.helpers.string_helper import random_email_generated, random_string_generator
from main.services.movie_api import MovieAPI
from main.services.user_api import UserAPI


def create_login_user(username, password, is_user_needed=False, is_token_needed=True):
    user_api = UserAPI()

    if is_user_needed:
        user_api.sign_up_user(username, password)

    if is_token_needed:
        user_api.login_user(username, password)
    return user_api


def create_user(username=random_email_generated(), password=random_string_generator(6)):
    user_api = UserAPI()
    user_api.sign_up_user(username, password)
    return user_api, UserData(username, password)


def delete_user(user_api, is_user_needed=True):
    if is_user_needed:
        user_api.delete_user()
    return user_api


def create_movie_by_user(user_api, movies_count=1):
    movie_api = MovieAPI(user_api.get_token_header())
    movie_data = []
    movie = []
    for index in range(movies_count):
        movie.append(MovieData())
        movie_data.append(movie_api.create_movie(movie[constants.Indexes.LAST_INDEX]))
    return movie_api, movie_data, movie
