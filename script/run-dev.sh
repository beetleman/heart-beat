#!/usr/bin/env bash
source /usr/local/nvm/nvm.sh

lein build < /dev/null &

TARGET=target/out/heard-beat.js
while `sleep 2`;
      if [ ! -f $TARGET ]; then
          echo "Waiting for Figwheel ...";
      else
          node $TARGET
      fi
do node ;
done;
