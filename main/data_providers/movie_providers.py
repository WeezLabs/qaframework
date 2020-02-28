def get_unsuccess_movie_data_provider():
    return [
        ["", [], []],
        ["test_create_movie", [], []],
        ["test_create_movie", ["drama"], []],
        ["test_create_movie", None, None],
        ["test_create_movie", ["drama"], None]
    ]


def get_movie_patch_data_provider():
    return [
        ["test_create_movie"],
        ["test_create_movie", ["drama"]]
    ]

