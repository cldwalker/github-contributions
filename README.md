## Description

This app shows a github user's forks and statistics about
their contributions to each fork. This is meant to improve on github's
contributions tab, which is limited in scope and time frame.
This app current only works off of a user's public information. This
means contributions can't be detected unless a user has a fork for it.

View the app [on heroku](https://github-contributions.herokuapp.com/).

## Running the App

1. Start the application: `GITHUB_AUTH=user:pass lein run`
2. Go to [localhost:8080](http://localhost:8080/) and look up a user's contributions.

## Configuration

This app takes the following environment variables:
* `$GITHUB_AUTH (required)` - This can either be your Github Basic auth
  `user:pass` or an oauth token.

## Limitations

* Only works with browsers that support [Server Side Events](http://caniuse.com/#feat=eventsource) and [HTML5 History](http://caniuse.com/#feat=history).
* In Chrome, if you look up a couple of different users and then enter
  a direct user url e.g. /defunkt, going backwords and forwards in
  your browser will be wonky.
