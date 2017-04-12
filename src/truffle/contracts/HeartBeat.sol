pragma solidity ^0.4.8;
import { IWithHeardBeat } from './IWithHeardBeat.sol';


contract HeartBeat {
  IWithHeardBeat[] callbacks; // array of all subscribers

  // Register subscriber
  function addSubscriber(IWithHeardBeat a) {
    callbacks.push(a);
  }

  function beat(uint time) {
    for(uint i = 0;i < callbacks.length; i++) {
      // all called subscribers must implement the oracleCallback
      callbacks[i].updateTime(time);
    }
  }
}
