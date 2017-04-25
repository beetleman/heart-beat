FROM mhart/alpine-node:latest

MAINTAINER Your Name <you@example.com>

# Create app directory
RUN mkdir -p /usr/src/heart-beat
WORKDIR /usr/src/heart-beat

# Install app dependencies
COPY package.json /usr/src/heart-beat
RUN npm install pm2 -g
RUN npm install

# Bundle app source
COPY target/release/heart-beat.js /usr/src/heart-beat
COPY public /usr/src/heart-beat/public

ENV HOST 0.0.0.0

EXPOSE 3000
CMD [ "pm2-docker", "/usr/src/heart-beat/heart-beat.js" ]
