const ConvertLib = artifacts.require("./ConvertLib.sol");
const MetaCoin = artifacts.require("./MetaCoin.sol");

const HeartBeat = artifacts.require("./HeartBeat.sol");
const Counter = artifacts.require("./Counter.sol");

module.exports = async function(deployer, network, accounts) {
    deployer.deploy(ConvertLib);
    deployer.link(ConvertLib, MetaCoin);
    deployer.deploy(MetaCoin);

    await deployer.deploy(HeartBeat);
    await deployer.deploy(Counter);

    const heatBeat = await HeartBeat.deployed();
    const counter = await Counter.deployed();

    heatBeat.addSubscriber(counter.address, {from: accounts[0]});
};
