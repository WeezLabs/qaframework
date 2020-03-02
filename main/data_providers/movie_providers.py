from main.helpers.string_helper import random_string_generator


def get_unsuccess_movie_data_provider():
    return [
        {"name": "", "genres": [], "casts": []},
        {"name": "test_create_movie", "genres": [], "casts": []},
        {"name": "test_create_movie", "genres": ["drama"], "casts": []},
        {"name": "test_create_movie"},
        {"name": "test_create_movie", "genres": ["drama"]}
    ]


def get_unsuccess_movie_patch_data_provider():
    return [
        {"name": ""},
        {"genres": []},
        {"casts": []}
    ]


def get_movie_patch_data_provider():
    return [
        {"name": "test_create_movie"},
        {"genres": ["drama"]},
        {"casts": [random_string_generator()]}
    ]

