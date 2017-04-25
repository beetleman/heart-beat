pragma solidity ^0.4.8;
import { IWithHeartBeat } from './IWithHeartBeat.sol';


contract HeartBeat {
  IWithHeartBeat[] callbacks; // array of all subscribers

  // Register subscriber
  function addSubscriber(IWithHeartBeat a) {
    callbacks.push(a);
  }

  function beat(uint time) {
    for(uint i = 0;i < callbacks.length; i++) {
      // all called subscribers must implement the oracleCallback
      callbacks[i].updateTime(time);
    }
  }
}
