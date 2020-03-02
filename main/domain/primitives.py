import json
import requests
import settings


def get(sub_path, header):
    return requests.get(settings.API_HOST_URL + sub_path, headers=header)


def post(sub_path, header, body):
    return requests.post(settings.API_HOST_URL + sub_path, headers=header, json=body)


def delete(sub_path, header):
    return requests.delete(settings.API_HOST_URL + sub_path, headers=header)


def put(sub_path, header, body):
    return requests.put(settings.API_HOST_URL + sub_path, headers=header, json=body)


def patch(sub_path, header, body):
    return requests.patch(settings.API_HOST_URL + sub_path, headers=header, json=body)
