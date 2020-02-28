def get_unsuccess_login_data_provider():
    return [
        ["", ""],
        ["mberesneva@distillery.com", "qwerty"],
        ["test", "test"]
    ]


def get_success_login_data_provider():
    return [
        ["mberesneva@distillery.com", "password"],
    ]


def get_success_delete_user_data_provider():
    return [
        ["mberesneva+delete@distillery.com", "password"],
    ]


def get_success_create_user_data_provider():
    return [
        ["mberesneva+create@distillery.com", "password"],
    ]


def get_unsuccess_create_user_data_provider():
    return [
        ["", ""],
        ["mberesneva@distillery.com", "password"]
    ]
