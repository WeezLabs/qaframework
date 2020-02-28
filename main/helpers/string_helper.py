import string
import random


def random_string_generator(string_length=10):
    letters = string.ascii_lowercase
    return ''.join(random.choice(letters) for i in range(string_length))


def random_email_generated():
    return random_string_generator(6) + '@' + random_string_generator(5) + ".test"
