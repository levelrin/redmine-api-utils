FROM ruby:2.6.8-bullseye

# Install Ruby on Rails
RUN gem install rails -v 5.2.6

# Install Redmine
ENV REDMINE_HOME=/usr/local/redmine
WORKDIR /usr/local
RUN git clone -b 4.1-stable https://github.com/redmine/redmine.git
WORKDIR $REDMINE_HOME
RUN touch config/database.yml
RUN echo 'production:\n\
  adapter: postgresql\n\
  database: redmine\n\
  host: redmine-db\n\
  username: postgres\n\
  password: "postgres"\n\
  encoding: utf8' > config/database.yml
RUN bundle install --without development test
RUN bundle exec rake generate_secret_token
RUN mkdir -p tmp tmp/pdf public/plugin_assets
