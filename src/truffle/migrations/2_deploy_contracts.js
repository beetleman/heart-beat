const ConvertLib = artifacts.require("./ConvertLib.sol");
const MetaCoin = artifacts.require("./MetaCoin.sol");

const HeartBeat = artifacts.require("./HeartBeat.sol");
const Counter = artifacts.require("./Counter.sol");

module.exports = function(deployer, network, accounts) {
    deployer.deploy(ConvertLib);
    deployer.link(ConvertLib, MetaCoin);
    deployer.deploy(MetaCoin);

    let heatBeat;

    deployer.deploy(HeartBeat)
        .then(() => deployer.deploy(Counter))
        .then(() => HeartBeat.deployed())
        .then((instance) => {
            heatBeat = instance;
            return Counter.deployed();
        })
        .then((counter) => {
            console.log("counter", counter.address);
            return heatBeat.addSubscriber(counter.address, {from: accounts[0]});
        });
};
