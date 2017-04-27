const HeartBeat = artifacts.require("./HeartBeat.sol");
const Counter = artifacts.require("./Counter.sol");

module.exports = function(deployer, network, accounts) {
    let counter;
    let heatBeat;

    deployer.deploy(HeartBeat)
        .then(() => deployer.deploy(Counter))
        .then(() => deployer.deploy(HeartBeat))
        .then(() => HeartBeat.deployed())
        .then((instance) => {
            heatBeat = instance;
            return Counter.deployed();
        })
        .then((instance) => {
            counter = instance;
            heatBeat.addSubscriber(counter.address, {from: accounts[0]});
        });
};
