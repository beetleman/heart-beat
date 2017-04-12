pragma solidity ^0.4.2;

import "truffle/Assert.sol";
import "truffle/DeployedAddresses.sol";
import { HeartBeat } from "../contracts/HeartBeat.sol";
import { Counter } from "../contracts/Counter.sol";

contract TestHeartBeat {

  function testBeatUsingDeployedContract() {
    HeartBeat heartbeat = HeartBeat(DeployedAddresses.HeartBeat());
    Counter counter1 = new Counter();
    Counter counter2 = new Counter();

    heartbeat.addSubscriber(counter1);
    heartbeat.beat(42);

    heartbeat.addSubscriber(counter2);
    heartbeat.beat(424);

    Assert.equal(counter1.counter(), 2, "Counter1 called 2 times");
    Assert.equal(counter2.counter(), 1, "Counter2 called 1 times");
  }

}
