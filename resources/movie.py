from flask import Response, request
from flask_jwt_extended.exceptions import NoAuthorizationError

from database.models import Movie, User
from flask_jwt_extended import jwt_required, get_jwt_identity
from flask_restful import Resource
from mongoengine.errors import FieldDoesNotExist, NotUniqueError, DoesNotExist, ValidationError, InvalidQueryError
from resources.errors import SchemaValidationError, MovieAlreadyExistsError, InternalServerError, \
    UpdatingMovieError, DeletingMovieError, MovieNotExistsError, UnauthorizedError


class MoviesApi(Resource):
    def get(self):
        query = Movie.objects()
        movies = Movie.objects().to_json()
        return Response(movies, mimetype="application/json", status=200)

    @jwt_required
    def post(self):
        try:
            user_id = get_jwt_identity()
            body = request.get_json()
            user = User.objects.get(id=user_id)
            movie = Movie(**body, added_by=user)
            movie.save()
            user.update(push__movies=movie)
            user.save()
            id = movie.id
            return {'id': str(id)}, 200
        except NoAuthorizationError:
            raise NoAuthorizationError
        except (FieldDoesNotExist, ValidationError):
            raise SchemaValidationError
        except NotUniqueError:
            raise MovieAlreadyExistsError
        except Exception as e:
            raise InternalServerError


class MovieApi(Resource):
    @jwt_required
    def put(self, id):
        try:
            user_id = get_jwt_identity()
            movie = Movie.objects.get(id=id, added_by=user_id)
            body = request.get_json()
            if body["name"] == "" or body["name"] is None:
                raise SchemaValidationError
            if body["casts"] is None or len(body["casts"]) == 0:
                raise SchemaValidationError
            if body["genres"] is None or len(body["genres"]) == 0:
                raise SchemaValidationError
            movie.update(**body)
            return '', 200
        except InvalidQueryError:
            raise SchemaValidationError
        except DoesNotExist:
            raise UpdatingMovieError
        except NotUniqueError:
            raise MovieAlreadyExistsError
        except SchemaValidationError:
            raise SchemaValidationError
        except Exception:
            raise InternalServerError

    @jwt_required
    def patch(self, id):
        try:
            user_id = get_jwt_identity()
            movie = Movie.objects.get(id=id, added_by=user_id)
            body = request.get_json()
            if "name" in body and body["name"] == "":
                raise SchemaValidationError
            if "casts" in body and len(body["casts"]) == 0:
                raise SchemaValidationError
            if "genres" in body and len(body["genres"]) == 0:
                raise SchemaValidationError
            movie.update(**body)
            return '', 200
        except InvalidQueryError:
            raise SchemaValidationError
        except SchemaValidationError:
            raise SchemaValidationError
        except DoesNotExist:
            raise UpdatingMovieError
        except Exception:
            raise InternalServerError

    @jwt_required
    def delete(self, id):
        try:
            user_id = get_jwt_identity()
            movie = Movie.objects.get(id=id, added_by=user_id)
            movie.delete()
            return '', 200
        except DoesNotExist:
            raise DeletingMovieError
        except Exception:
            raise InternalServerError

    def get(self, id):
        try:
            movies = Movie.objects.get(id=id).to_json()
            return Response(movies, mimetype="application/json", status=200)
        except DoesNotExist:
            raise MovieNotExistsError
        except Exception:
            raise InternalServerError
