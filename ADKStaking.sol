// SPDX-License-Identifier: GPL-3.0
pragma solidity >0.8.4;

// ADK Staking Contract - Mainnet v2.1

// Allows ADK Owners to STAKE and UNSTAKE ADK
//
// ADK are staked  by sending ADK to this contract (receive function) or by calling the Stake function.
// The staked amount is registered against the sending address and only the sending address
// will be able to unstake the ADK again after a set minimum period. Initially this period is
// set to 5760 blocks (one ADK epoch - at time of writing). The period is reset with each staking action.
//

contract ADKStake {

    mapping (address => uint256) public StakedAmountByAddress;    
    mapping (address => uint256) public StakedBlockByAddress;    

    address public owner ;
    uint256 public lock_period_blocks;

    constructor() {
        owner = msg.sender; 
        lock_period_blocks = 5760;
    }
    
    /////////////////////////////////////
    // STAKING 
    //
    // In order to stake ADK an address needs to send ADK to the Stake function (or just to the 
    // generic receive() function, simply by sending ADK to the Stake contract)
    //
    // Each stake action reset's and address unstake period.
    
    bool mutex_receive = false;

    function Stake() public payable {

         require (!mutex_receive, "receive/Stake(): no reentry allowed");
         mutex_receive = true;
         require(msg.value != 0, "ADK value cannot be 0");
         StakedAmountByAddress[msg.sender] += msg.value; 
         StakedBlockByAddress[msg.sender] = block.number; // remember last staked milestone, as unstaking is only possible after a while
         emit EVT_ADKSTAKE(msg.sender, msg.value, block.number); //solhint-disable-line indent, no-unused-vars
                 
         mutex_receive = false;
    }

    receive() external payable { // receive ADK, Auto Stake
        Stake();
    }
    

   /////////////////////////////////////
   // UNSTAKING
   //
   // This function can be called in order to unstake staked ADK
   
   bool mutex_unstake = false;

   function Unstake(uint256 _amountToUnstake) public {
        require (!mutex_unstake, "Unstake(): no reentry allowed");
        mutex_unstake = true;
        
        require (_amountToUnstake > 0, "Unstake(): invalid amount claimed");
        require (StakedAmountByAddress[msg.sender] >= _amountToUnstake, "Unstake(): msg.sender has nothing staked");
        require (StakedBlockByAddress[msg.sender] + lock_period_blocks <= block.number, "Unstake(): unstake period not completed");
        
        StakedAmountByAddress[msg.sender]-=_amountToUnstake;

        payable(msg.sender).transfer(_amountToUnstake); 
        emit EVT_ADKUNSTAKE(msg.sender, _amountToUnstake, block.number); //solhint-disable-line indent, no-unused-vars
        mutex_unstake = false;
   }
      
    // returns currently staked ADK (total across all staking addresses)
    function TotalADKStaked() public view returns (uint256) {
        return address(this).balance;
    }
    
    // returns the staked amount for the caller
    function GetStakedCurrentAddress() public view returns (uint256){
        return StakedAmountByAddress[msg.sender];
    }

    // returns the number of blocks caller has to wait until unstaking is possible
    function GetWaitCurrentAddress() public view returns (uint256){
        if (StakedBlockByAddress[msg.sender] + lock_period_blocks <= block.number) {
            return 0; // no waiting
        } else {
            return StakedBlockByAddress[msg.sender] + lock_period_blocks - block.number;
        }
         
    }

    // EVENTS

    event EVT_ADKSTAKE(address, uint256,uint256);
    
    event EVT_ADKUNSTAKE(address, uint256,uint256);
    
    // ADMIN FUNCTIONS

    function SetMinStakePariod(uint256 period) public onlyOwner{
          lock_period_blocks = period;
    }

    function SetOwner(address _newOwner) public onlyOwner{
          owner = _newOwner;
    }

    // MODIFIER
    
    modifier onlyOwner {
        require(msg.sender == owner, "NOT CALLED BY CONTRACT OWNER");
        _;
    }
   
  

}