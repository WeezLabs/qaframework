from main.data_providers.movie_providers import get_unsuccess_movie_data_provider
from main.data_providers.user_providers import get_unsuccess_login_data_provider, get_success_login_data_provider, \
    get_success_create_user_data_provider, get_unsuccess_create_user_data_provider


def generate_test_data_provider(metafunc):
    if 'unsuccess_login_data_provider' in metafunc.fixturenames:
        metafunc.parametrize("unsuccess_login_data_provider", get_unsuccess_login_data_provider())
    if 'success_login_data_provider' in metafunc.fixturenames:
        metafunc.parametrize("success_login_data_provider", get_success_login_data_provider())
    if 'success_create_user_data_provider' in metafunc.fixturenames:
        metafunc.parametrize("success_create_user_data_provider", get_success_create_user_data_provider())
    if 'unsuccess_create_user_data_provider' in metafunc.fixturenames:
        metafunc.parametrize("unsuccess_create_user_data_provider", get_unsuccess_create_user_data_provider())
    if 'unsuccess_movie_data_provider' in metafunc.fixturenames:
        metafunc.parametrize("unsuccess_movie_data_provider", get_unsuccess_movie_data_provider())

