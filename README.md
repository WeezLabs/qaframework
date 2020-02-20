# server
python server

 1. brew install pipenv
 2. go to project directory
 3. pipenv install flask
 4. pipenv install flask-mongoengine
 5. pipenv install flask-restful
 6. pipenv install flask-bcrypt
 7. pipenv instll flask-jwt-extended
 8. export ENV_FILE_LOCATION=./.env
 9. pipenv install flask-mail
 
 start smtp server
 python -m smtpd -n -c DebuggingServer localhost:1025
 
 MongoDB
 1. brew tap mongodb/brew
 2. brew install mongodb-community@4.2
 3. brew services start mongodb-community@4.2
 
 MongoDB viewer
 https://www.mongodb.com/download-center/compass
 
 start server
 1. pipenv shell
 2. python run.py
