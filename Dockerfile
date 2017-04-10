FROM mhart/alpine-node:latest

MAINTAINER Your Name <you@example.com>

# Create app directory
RUN mkdir -p /usr/src/heard-beat
WORKDIR /usr/src/heard-beat

# Install app dependencies
COPY package.json /usr/src/heard-beat
RUN npm install pm2 -g
RUN npm install

# Bundle app source
COPY target/release/heard-beat.js /usr/src/heard-beat
COPY public /usr/src/heard-beat/public

ENV HOST 0.0.0.0

EXPOSE 3000
CMD [ "pm2-docker", "/usr/src/heard-beat/heard-beat.js" ]
