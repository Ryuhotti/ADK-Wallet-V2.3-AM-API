# ADKStaking.sol

<b>ADKStaking.sol</b> is the core smart contract facilitating Staking/Unstaking on the ADK v2.1 mainnet

Staking Contract: <b>0x56287E079a6B6752cf1Da9747f0Fde51118b8420</b>

##Manual staking/unstaking:

### Staking

ADK owners can stake by sending ADK to the <b>Staking Smart Contract</b> - either by directly sending ADK to the staking contract address, or by calling the stake function of the contract. The contract will record staked amount and last stake block number against the address.

### Unstaking
ADK owners of staked ADK can unstake ADK by calling the contract function Unstake(amount_to_unstake). Provided that the minimum period (number of blocks) since the last staking action of the address has passed, the requested staked ADK are returned to the sender.

Note: Staked ADK can only be unstaked by and returned to the SAME address which initially staked the ADK.

##Staking and Unstaking via ADKWALLET-GUI and ADKWALLET-CLI
Stake and Unstake function call will be implemented into adkwallet-cli and adkwallet-gui very soon(tm)


## Why Stake ADK

(NEAR) FUTURE BENEFITS! Continuous staking periods and amount staked (factored together) are recorded by <b>Contract Events</b> and will flow into the calculations for the upcoming PoS nodes as well as other benefits (yet to be anounced so stay tuned)
