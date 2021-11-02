### Motivation

We want to set up the Redmine server to check the actual API behaviors during the development.

### Prerequisite

To follow this document, we need to install the following:
1. [Docker](https://docs.docker.com/get-docker/)
2. [Docker Compose](https://docs.docker.com/compose/install/)

### Setup

First, we need to create a directory to store data used by Redmine.
Please run this command:
```
mkdir pgdata
```

Next, we should build a Redmine Docker image.
There is a `Dockerfile` in this directory. We can use it to build an image like this:
```
docker build -t redmine-web:1 .
```

After the build, let's run the Redmine container in an interactive mode like this:
```
docker run --rm --name redmine-web -it redmine-web:1 /bin/bash
```

We need to run the Postgres to use the Redmine application.
Let's open the new terminal and run the database container like this:
```
docker run --rm -d -p 5432:5432 --name redmine-db -v ${PWD}/pgdata:/usr/share/pgdata -e POSTGRES_PASSWORD=postgres -e PGDATA=/usr/share/pgdata postgres:13.2
```
Then, we need to create a role and database. Let's get into the Postgres shell in an interactive mode like this:
```
docker container exec -it redmine-db psql -U postgres
```
In the Postgres shell, let's execute the following commands:
```
CREATE ROLE redmine LOGIN ENCRYPTED PASSWORD 'my_password' NOINHERIT VALID UNTIL 'infinity';
CREATE DATABASE redmine WITH ENCODING='UTF8' OWNER=redmine;
exit
```

The database is ready. Let's configure the network so that the Redmine container can communicate with the database container.
Please execute the following commands:
```
docker network create redmine-setup
docker network connect redmine-setup redmine-web
docker network connect redmine-setup redmine-db
```

Let's go back to the Redmine container, which is in the interactive mode.
We should execute the following commands:
```
RAILS_ENV=production bundle exec rake db:migrate
RAILS_ENV=production REDMINE_LANG=en bundle exec rake redmine:load_default_data
exit
```

Everything is ready! Let's do some cleanup like this:
```
docker stop redmine-db
docker network rm redmine-setup
```

### Run the Redmine server

We can easily run the Redmine server like this:
```
docker-compose up
```

We can see the Redmine page at `http://localhost:3000/`.

To stop the server, we can press `ctrl+C` on the keyboard and execute this command:
```
docker-compose down
```

### Reference

[Installing Redmine](https://www.redmine.org/projects/redmine/wiki/redmineinstall)
