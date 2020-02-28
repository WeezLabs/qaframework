import json

import jsonpickle

from main.domain import primitives
from main.services.base_api import BaseApi


class UserAPI(BaseApi):
    sub_path = "/auth"

    def sign_up_user(self, username, password):
        response = primitives.post(self.sub_path + "/signup", {},
                                   {"email": username,
                                    "password": password})
        user = jsonpickle.decode(response.content)
        return response.status_code, user

    def login_user(self, username, password):
        user = {}
        response = primitives.post(self.sub_path + "/login", {},
                                   {"email": username,
                                    "password": password})

        if response.status_code == 200:
            user = jsonpickle.decode(response.content)
            self.token_header = {"Authorization": "Bearer " + response.json()["token"]}
        return response.status_code, user

    def delete_user(self):
        response = primitives.delete(self.sub_path + "/delete", self.token_header)
        return response.status_code
