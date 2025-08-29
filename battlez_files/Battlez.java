/*
 * @title: Battlez;Constructor
 * @date: Jan 8th, 2023
 * @version: 1.0
 */
package summative_OopBattlez;

public class Battlez {

	//instance variables (global variables that will be used on the character)
	private String name;
	private String ultimateName;//stores ultimate name
	private String ultimateDescription;//stores quick description of ultimate
	private double dmgTaken;//keeps track of damage taken
	private double netHealth;//keeps track of starting health
	private double health;
	private double defPool;//stores base defense and added on temporarily when player defends 
	private double atkDmgPool;//stores damage pool when users turn is over
	private double atkDmg;//base damage for character
	private int critHits;//stores # of critical hits
	private double def;//defense is a coefficient to reduce dmg, lower means better defense (defense is a inverse relation)
	private boolean reflect;
	private boolean defProof;
	private boolean atkDmgProof;
	private boolean ultimate;
	private boolean ultimateUsed;//controls whether user has used ultimate already (user can only use ultimate once)
	private boolean retired;//determines whether the user has been defeated or not
	
	//variables after an interaction has taken place
	private String clashMessage;
	private String clashReportMessage;
	private double clashDmg;//stores 1 value after the clash has taken place
	
	//used for computer/information storage
	private double riskFactor=0;
	private double rewardFactor=0;
	private int atks=0;
	private int defs=0;
	private int reflects=0;
	
	
	
	
	//empty constructor for objects that will be filled later on in program (after characters are created)
	Battlez (){
		
	}
	
	Battlez (String name, double health, double atkDmg, double defPool, double def) {
		
		this.name=name;
		this.ultimateName=setUltimateName();
		this.health=health;
		this.atkDmg=atkDmg;
		this.def=def;
		this.defPool=defPool;
		
		this.netHealth=this.health;
		this.critHits=0;
		this.reflect=false;
		this.defProof=false;
		this.atkDmgProof=false;
		this.ultimate=false;
		this.ultimateUsed=false;
		this.retired=false;
		
	}
	
	
	
	
	//behavioural methods
	
	
	//PLAYER ULTIMATES
	
	public double useUltimate(double enemyHealth) {
		clashMessage=ultimateMessage();
		if (ultimateName.equalsIgnoreCase("Judgement Cut")) {
			return judgementCut(enemyHealth);
		}
		else if (ultimateName.equalsIgnoreCase("Secret Sensation")) {
			secretSensation();
		}
		else if (ultimateName.equalsIgnoreCase("Spartan Rage")) {
			spartanRage();
		}
		else if (ultimateName.equalsIgnoreCase("Its Spongin Time")) {
			itsSponginTime();
		}
		return 0;
	}
	
	//the character Vergil's ultimate attack, takes away 50% of the opponents health
	private double judgementCut(double enemyHealth) {
		if (name.equalsIgnoreCase("Vergil") && checkUltimate()==true && ultimateUsed==false) {
			ultimateUsed=true;
			double dmgDealt=(enemyHealth*=0.50);
			enemyHealth-=dmgDealt;//reduces enemy's health by 50%
			
			return dmgDealt;
		}
		return 0;
	}

	//the character Ultra Instinct Goku's ultimate attack, permanent defense pool 55% less dmg
	private void secretSensation() {
		if (name.equalsIgnoreCase("Ultra Instinct Goku") && checkUltimate()==true && ultimateUsed==false) {
			ultimateUsed=true;
			this.defPool=0.45;
		}
	}
	
	//the character Kratos's ultimate attack, permanent base dmg increase to 80
	private void spartanRage() {
		if (name.equalsIgnoreCase("Kratos") && checkUltimate()==true && ultimateUsed==false) {
			ultimateUsed=true;
			this.atkDmg=80;
		}
	}
	
	//the character Spongebob's ultimate attack, IMMEDIATE DEATH
	private void itsSponginTime() {
		if (name.equalsIgnoreCase("Spongebob") && checkUltimate()==true && ultimateUsed==false) {
			ultimateUsed=true;
			this.atkDmg=9999;
		}
	}
	
	//sets the ultimate name for the showdown report
	private String setUltimateName() {
		if (name.equalsIgnoreCase("Vergil")) {
			ultimateName="Judgement Cut";
			ultimateDescription=" (deal an unblockable 50% damage to opponent)";
		}
		else if (name.equalsIgnoreCase("Ultra Instinct Goku")) {
			ultimateName="Secret Sensation";
			ultimateDescription=" (base defense reduces damage by 55%)";
		}
		else if (name.equalsIgnoreCase("Kratos")) {
			ultimateName="Spartan Rage";
			ultimateDescription=" (base damage increased by 50%)";
		}
		else if (name.equalsIgnoreCase("Spongebob")) {
			ultimateName="Its Spongin Time";
			ultimateDescription=" (immediate death on next attack)";
		}
		return this.ultimateName;
	}
	
	//validates usage of ultimate
	public boolean checkUltimate() {
		if (critHits>=3) {
			this.ultimate=true;
			return true;
		}
		return false;
	}
	//PLAYER ULTIMATES END

	
	
	//clash report methods (use in interaction to display actions that occurred)
	private String ultimateMessage() {
		return name+" has used "+ultimateName+ultimateDescription+"! ";
	}
	private String defendMessage() {
		return name+" defended! ";
	}
	private String reflectMessage() {
		return name+ " has reflected! ";
	}
	private String attackMessage() {
		return name+" attacked! ";
	}
	private String criticalHitMessage() {
		return name+" attacked and got a critical hit! ";
	}
	private String responseMessage(double enemyDmgTaken) {
		return "That dealt "+enemyDmgTaken+" damage to ";
	}
	
	//takes clash damage from opponent and creates message outlining the interaction
	public String displayClashReport(double enemyDmgTaken, String name) {
		double tempEnemyDmgTaken=(Math.round(enemyDmgTaken)*1000.0)/1000.0;//temp storage for accuracy
		//if there is no damage, it will simply list the action done during the interaction
		if (clashMessage.equalsIgnoreCase(ultimateMessage()) && enemyDmgTaken<=0 || enemyDmgTaken<=0){
			clashReportMessage=clashMessage;
		}
		else {
			clashReportMessage=clashMessage+responseMessage(tempEnemyDmgTaken)+name+"!";
		}
		return clashReportMessage;
	}
	
	
	
	
	//attack feature 
	public double attack() {
		//fail-safe to stop the attack pool from unnecessarily adding on damage (damage stack)
		ultimate=false;
		if (atkDmgProof==false) {
			atks++;
			atkDmgProof=true;
			int rand = (int) (Math.random()*100);//random number 0 to 100
			double critHit=this.atkDmg;
			if (rand>70) {//user has 30% of getting a critical
				critHit*=1.30;//increases dmg by 30%
				atkDmgPool+=critHit;//adds to atkDmgPool
				this.critHits++;//adds to critical hits
				clashMessage=criticalHitMessage();
				return atkDmgPool;
			}
			atkDmgPool+=atkDmg;//adds to damage pool
		}
		clashMessage=attackMessage();
		return atkDmgPool;
	}
	
	//only for use in constructor
	private void atkDmgReset() {
		if (atkDmgProof==true) {
			atkDmgPool=0;//resets damage pool after interaction is user
			atkDmgProof=false;
		}
	}
	
	//adds to defense pool and validates proof
	public double defend(double enemyDmg){
		int rand = (int) (Math.random()*100); 
		clashMessage=defendMessage();
		if (defProof==false) {
			defProof=true;
			this.defPool+=this.def;
			//50% chance of reflecting damage from enemy
			if (rand>50) {
				clashMessage=reflectMessage();
				reflect=true;
				reflects++;
				return enemyDmg*=1.8;//reflects enemy damage and deals more back
			}
			defs++;
		}
		return 0;//returns zero because no damage is being done to opponent
	}
	
	//can only be used if defense proof is validated
	private void defReset() {
		if (defProof==true) {
			this.defPool-=this.def;
			defProof=false;
			reflect=false;
		}
	}
	
	//deals with interaction between players
	public void clash(double enemyDmg, boolean enemyUltimateUsed, boolean enemyUltimate, boolean enemyReflect) {
		//if the opponent has reflected the attack, all the damage will be returned immediately
		//left part of "or" section is specifically for the character Vergil, he has an unblockable 20% damage move
		if (enemyUltimateUsed==true && enemyUltimate==true || enemyReflect==true) {
			clashDmg=enemyDmg;//used in damage report for the interaction
			dmgTaken+=clashDmg;
			this.health-=enemyDmg;
		}
		//reduces (or in some cases, increases) damage done by user using the defense pool 
		else {
			clashDmg=(enemyDmg*defPool);
			dmgTaken+=clashDmg;
			this.health-=(enemyDmg*defPool);
		}
		defReset();
		atkDmgReset();
	}
	
	//determines whether a player has been defeated; it is set to 0.99 because the user will see 0.0 even if they have
	//health albeit decimal health (health=0.754 will show 0.0 for the user); everything below 1 is considered as death
	public boolean retired() {
		if (health<=0.99) {
			this.retired=true;
			return retired;
		}
		return retired;	
	}
	
	
	public String displayRoundInfo(double dmgDealt) {
		//temporary variables which round to two decimals in order to save accuracy 
		double tempHealth=(Math.round(health)*1000.0)/1000.0;
		double tempDmgDealt=(Math.round(dmgDealt)*1000.0)/1000.0;
		double tempDmgTaken=(Math.round(dmgTaken)*1000.0)/1000.0;
		
		return "\n\tCharacter: "+name+"\n\tStarting Health: "+netHealth+"\n\tEnding Health: "+tempHealth+"\n\t"
				+ "Damage Dealt: "+tempDmgDealt+"\n\tDamage Taken: "+tempDmgTaken+"\n\tCritical Hits: "
				+critHits+"\n\t# of Attacks: "+atks+"\n\t# of Defends: "+defs+"\n\t# of Reflects: "+reflects+""
				+"\n\tUltimate used: "+ultimateUsed;
	}
	
	public String displayInfo() {
		int defPoolPercent=(int) ((1-defPool)*100);
		int defPercent=(int) (def*100);
			return name+"\n\tHealth: "+health+"\n\tAttack Damage: "+atkDmg+"\n\tBase Defense: Reduce damage by "
					+ defPoolPercent+"%\n\tDefense Factor: Adds +"+defPercent+"% reduction to base factor\n\t"
					+ "Ultimate: "+ultimateName+ultimateDescription;
	}
	
	public String toString() {
		return "Character: "+name+"\nAttack Damage: "+atkDmg+"\nBase Defense: "+defPool+"\nDefense Factor: -"+def+
				"\nUltimate: "+ultimateName+"\n";
	}
	
	
	
	
	//computer thinking section start
	
	/*
	 * computer determines risk vs reward based on enemy health, attack damage, and how many times it has attacked 
	 * or defended. risk makes the computer more likely to block, reward makes it more likely to attack
	 */
	public void determineRisk(double enemyHealth, double enemyAtkDmg) {
		//resets reward factor so computer gets a fresh mind if the reward factor is too high
		if (rewardFactor>9999 && rewardFactor>riskFactor) {
			rewardFactor=0;
			riskFactor=300;
		}
		//resets risk factor if computer keeps defending similar to above
		if (riskFactor>9999 && rewardFactor<riskFactor) {
			riskFactor=0;
			rewardFactor=300;
		}
		//makes computer attack more often
		if (atks+2<defs) {
			rewardFactor=riskFactor;
			rewardFactor/=100;
			
		}
		//health consideration
		if (enemyHealth>health) {
			riskFactor*=2;
			riskFactor++;
			//if computer is losing by a lot, it will switch plans to attack instead of defending
			if (health<enemyHealth*0.5 && atks*2<defs) {
				rewardFactor=riskFactor;
				rewardFactor+=riskFactor;
				rewardFactor*=riskFactor;
				riskFactor/=3;
			}

		}
		if (health<enemyHealth*0.8 && atks<def) {
			rewardFactor=riskFactor;
			rewardFactor+=riskFactor;
			riskFactor/=2;
		}
		//if the computer continuously defends for 3 turns, it will periodically attack
		if (atks+3<def) {
			rewardFactor*=riskFactor;
			riskFactor/=1.2;
		}
		//section specifically for spongebob to be more aggressive 
		//if he doesn't have his ultimate and has defended more often
		if (atks<def && critHits<3 && name.equalsIgnoreCase("Spongebob")) {
			riskFactor=0;
			rewardFactor=1;
			rewardFactor*=1000;
		}
		//if attack damage is much more than the player, computer will immediately attack
		if (atkDmg>enemyAtkDmg*7) {
			rewardFactor+=100;
		}
		if (enemyAtkDmg>atkDmg) {
			riskFactor*=2;
			riskFactor++;
			//if the enemy cannot do more than 10% damage, the computer will find incentive to attack
			if (enemyAtkDmg*defPool<health*0.10) {
				rewardFactor*=2;
				rewardFactor++;
			}
		}
		//if the enemy's health is half the computer's health, the computer will find incentive to attack
		if (enemyHealth*2<health) {
			rewardFactor*=2;
		}
		//if the computer sees that it hasn't used the ultimate and has enough critical to attack,
		//it will find incentive to attack more often after it uses the ultimate
		if (ultimateUsed==false && critHits>3) {
			rewardFactor*=2;
			rewardFactor++;
			if (checkUltimate()==true) {
				rewardFactor*=2;
				rewardFactor++;
			}
		}
		//if the computer has more health, it will find incentive to attack
		if (enemyHealth<health) {
			rewardFactor*=2;
			rewardFactor++;
		}
		//if the computer does more damage, it will find incentive to attack
		if (enemyAtkDmg<atkDmg) {
			//if the player cannot do more than 10% damage, it will find incentive to attack just like
			//how it is with the health incentive
			if (enemyAtkDmg*defPool<health*0.10) {
				rewardFactor*=2;
				rewardFactor++;
			}
		}
		//if the computer is one hit away from getting 3 critical hits, it will become extremely aggressive
		if (critHits+1==3) {
			rewardFactor+=riskFactor;
			riskFactor/=100;
		}
		//if the player is one shot away from being defeated, the computer is more likely to attack it
		if (atkDmg>enemyHealth) {
			rewardFactor+=10000;
		}
	}
	
	public double actionChoice(double enemyHealth, double enemyAtkDmg) {
		//if the ultimate is available, the computer will immediately use it (more incentive)
		if (checkUltimate()==true && ultimateUsed==false) {
			return useUltimate(enemyHealth);
		}
		determineRisk(enemyHealth, enemyAtkDmg);
		if (riskFactor>rewardFactor) {
			return defend(enemyAtkDmg);
		}
		else if (riskFactor<rewardFactor) {
			return attack();
		}
		else if (rewardFactor==riskFactor) {
			int choice=(int) (Math.random()*2+1);
			if (choice==1) {
				return attack();
			}
			else if(choice==2) {
				return defend(enemyAtkDmg);
			}
		}
		return 0;
	}
	
	//computer section end

	
	
	
	
	//setters and getters
		public String getName() {
			return name;
		}

		public String getClashMessage() {
			return clashMessage;
		}

		public double getClashDmg() {
			return clashDmg;
		}

		public void setName(String name) {
			this.name = name;
		}
		
		public double getDmgTaken() {
			return dmgTaken;
		}

		public double getNetHealth() {
			return netHealth;
		}

		public double getHealth() {
			return tempGetHealth();
		}
		
		//use to lie to user about health in order to keep accuracy with decimal places
		private double tempGetHealth() {
			double tempHealth=(Math.round(health)*1000.0)/1000.0;
			return tempHealth;
		}

		public double getDefPool() {
			return defPool;
		}

		public double getAtkDmgPool() {
			return atkDmgPool;
		}

		public double getAtkDmg() {
			return atkDmg;
		}

		public int getCritHits() {
			return critHits;
		}

		public double getDef() {
			return def;
		}
		
		public boolean getDefProof() {
			return defProof;
		}

		public boolean getUltimate() {
			return ultimate;
		}

		public boolean getUltimateUsed() {
			return ultimateUsed;
		}
		
		//returns reflect damage
		public boolean getReflect() {
			return this.reflect;
		}
		
	
}
