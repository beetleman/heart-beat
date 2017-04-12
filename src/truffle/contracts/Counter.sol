pragma solidity ^0.4.8;
import { IWithHeardBeat } from './IWithHeardBeat.sol';

contract Counter is IWithHeardBeat {
  uint public counter = 0;

  function updateTime(uint _time) external {
    counter++;
  }

}
