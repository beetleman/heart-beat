const ConvertLib = artifacts.require("./ConvertLib.sol");
const MetaCoin = artifacts.require("./MetaCoin.sol");

const HeartBeat = artifacts.require("./HeartBeat.sol");
const Counter = artifacts.require("./Counter.sol");

module.exports = async function(deployer, network, accounts) {
    deployer.deploy(ConvertLib);
    deployer.link(ConvertLib, MetaCoin);
    deployer.deploy(MetaCoin);

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
