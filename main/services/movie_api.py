import jsonpickle

from main.data.movie_data import MovieData
from main.domain import primitives
from main.services.base_api import BaseApi


class MovieAPI(BaseApi):
    sub_path = "/movies"

    def __init__(self, token_header):
        self.token_header = token_header

    def create_movie(self, movie=None):
        movie_data = {}
        if movie is None:
            movie = MovieData().__dict__
        response = primitives.post(self.sub_path, self.token_header, movie)
        if response.status_code == 200:
            movie_data = jsonpickle.decode(response.content)
        return response.status_code, movie_data

    def update_movie(self, movie_id, movie):
        response = primitives.put(self.sub_path + "/" + movie_id, self.token_header, movie)
        if response.status_code == 200:
            movie = jsonpickle.decode(response.content)
        return response.status_code, movie

    def patch_movie(self, movie_id, movie):
        response = primitives.patch(self.sub_path + "/" + movie_id, self.token_header, movie)
        if response.status_code == 200:
            movie = jsonpickle.decode(response.content)
        return response.status_code, movie

    def delete_movie(self, movie_id):
        response = primitives.delete(self.sub_path + "/" + movie_id, self.token_header)
        return response.status_code

    def get_movie(self, movie_id):
        movie = {}
        response = primitives.get(self.sub_path + "/" + movie_id, {})
        if response.status_code == 200:
            movie = jsonpickle.decode(response.content)
        return response.status_code, movie
