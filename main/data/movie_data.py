from main.enum.genres import get_genres
from main.helpers.string_helper import random_string_generator


class MovieData:
    name = ""
    casts = []
    genres = []
    id: str

    def __init__(self):
        self.name = random_string_generator()
        self.casts = [random_string_generator(5), random_string_generator(8), random_string_generator()]
        self.genres = [get_genres()[0]]

    def get_name(self):
        return self.name

    def get_casts(self):
        return self.casts

    def get_genres(self):
        return self.genres

    def get_id(self):
        return self.id

    def builder(self, name, genres, casts):
        self.name = name
        self.casts = casts
        self.genres = genres

