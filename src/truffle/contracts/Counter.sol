pragma solidity ^0.4.8;
import { IWithHeartBeat } from './IWithHeartBeat.sol';

contract Counter is IWithHeartBeat {
  uint public counter = 0;

  function updateTime(uint _time) external {
    counter++;
  }

}
