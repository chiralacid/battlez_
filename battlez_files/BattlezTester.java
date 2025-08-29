/*
 * @title: Battlez
 * @date: Jan 8th, 2023
 * @version: 1.0
 */
package summative_OopBattlez;

import java.util.ArrayList;
import java.util.Scanner;

public class BattlezTester {

	public static void main(String[] args) {
		
		Scanner userIn = new Scanner(System.in);
		ArrayList<Battlez> characters = new ArrayList<> ();
		Battlez player = new Battlez();
		Battlez comp = new Battlez();
		
		System.out.println("Welcome to Battlez!");
		while (true) {
			addCharacters(characters);//adds characters to array list
			welcomeMenu(userIn);
			int playerChoice = selectionMenu(characters, userIn);//stores user's choice of character
			//entered if player choice isn't -1, 1 is subtracted from in selection menu so by pressing 0
			//-1 is returned
			if (playerChoice!=-1) {
				player=characters.get(playerChoice);//sets user's character
				characters.remove(playerChoice);//removes character chosen by player(one character, one player)
				comp=characters.get(selectRandomCharacter(characters));//selects random character for computer
				System.out.println("\n\nITS TIME FOR A SHOWDOWNNNNNNNNN!!!!\n\n==============================="
						+ "\n\n"+player.getName()+" VS "+comp.getName()+"\n\n===============================\n");
				showdown(player, comp, userIn);
				
			}
			
		}
		
	}
	
	
	//welcome menu, used if user start new match
	public static void welcomeMenu(Scanner userIn) {
		while (true) {
			System.out.print("\n=========\nMAIN MENU\n\n1. Start a Match\n2. Help\n3. Exit\n\nOption: ");
			int choice=userInput(userIn, 1, 3);
			if (choice==1) {
				break;
			}
			else if (choice==2) {
				help(userIn);
			}
			else if (choice==3) {
				System.out.println("\n\nExiting Battlez...");
				System.exit(0);
			}
		}
	}
	
	//help menu for the user 
	public static void help(Scanner userIn) {
		loop: while (true) {
			System.out.print("\n====\nHelp Menu: \n\t1. Defense Mechanics\n\t2. Attack Mechanics\n\t3. Ultimate Mechanics\n\t4. Exit\n\nOption: ");
			int choice=userInput(userIn, 1, 4);
				switch (choice) {
					case 1:
						System.out.println("\nDefense Mechanics: Each character has a base coefficient for defense which starts at 1.00; "
								+ "\nthis coefficient passively reduces (or in some cases, increases) the amount of damage dealt by the opponent. "
								+ "\nEach character can choose to defend which will make their defense coefficient (defense pool) lower, in turn "
								+ "\nreducing the damage even more. The defense coefficient is an inverse relation meaning a lower coefficient is "
								+ "\nbetter. The defend move also has a 50% chance of reflecting, reflecting is a universal move which deals the"
								+ "\npossible damage plus 80% from the opponent back at them. Reflecting still applies even if both characters "
								+ "\nblock, however, it does not allow for critical hits to occur");
						break;
					case 2:
						System.out.println("\nAttack Mechanics: Each Character has a specific amount of damage they will deal if they attack, "
								+ "\nfor example, Vergil has the highest attack damage in the game with 55. The damage will always remain the "
								+ "\nsame unless the player gets a 'Critical hit', which is a 30% chance and increases the damage by 30%. "
								+ "\nUsing Vergil as an example, he will deal about 71.5 damage as opposed to 55.");
						break;
					case 3:
						System.out.println("\nUltimate Mechanics: The 'Ultimate' is a special move that is unique to each character. An ultimate "
								+ "\ncan be used once the user has reached 3 or more critical hits, the option for an ultimate will then "
								+ "\nautomatically appear. Ultimates may vary greatly in how they benifit the player, for example, Vergil's "
								+ "\nultimate will immeditely deal 50% damage to the oppponent and bypass any attempt at blocking. Though very"
								+ "\npowerful, ultimates can only be used a single time.");
						break;
					case 4:
						break loop;//refers to entire while loop instead of switch
				}
		}
		System.out.println();
	}
	
	
	//selection menu for player selecting a character
	public static int selectionMenu(ArrayList<Battlez> characters, Scanner userIn) {
		System.out.print("\n\nSelect your character: \n\n");
		for (int i=0; i<characters.size();i++) {
			System.out.println((i+1)+". "+characters.get(i).displayInfo()+"\n");
		}
		System.out.print("0. Back to Main Menu\n\nOption: ");
		//gets which character the user wants, subtract by one to account for index
		int choice=(userInput(userIn, 0, characters.size()))-1;
		return choice;
		
	}
	
	//selects random character for the computer to use
	public static int selectRandomCharacter(ArrayList<Battlez> characters){
		return (int) (Math.random()*characters.size());
	}
	
	//adds characters with their necessary balancing stats and name
	public static ArrayList<Battlez> addCharacters(ArrayList<Battlez> characters) {
		characters.clear();//clears arraylist for when user starts a new game
		//order: name, health, attack damage, base defense coefficient, defense coefficient boost 
		characters.add(new Battlez("Vergil", 850, 55, 0.85, 0.05));
		characters.add(new Battlez("Ultra Instinct Goku", 800, 35, 0.70, 0.05));
		characters.add(new Battlez("Kratos", 1000, 45, 1.05, 0.10));
		characters.add(new Battlez("Spongebob", 700, 25, 1.10, 0.10));
		
		return characters;
	}
	
	
	
	
	
	//randomizes messages when fight starts
	public static String showdownMessage() {
		//battles starting quotes; 5 quotes
		int choice=(int) (Math.random()*5+1);
		
		switch (choice) {
			case 1:
				return "A fierce battle is about to begin!";
			case 2: 
				return "Brace yourself for the coming battle!";
			case 3:
				return "Time to begin the match!";
			case 4:
				return "This should be a great fight!";
			//set as default so method will always return something
			default: 
				return "The outcome of this fight is anybody's guess!";
		}
	}
	
	
	//showdown between two challengers; fight section where computer and player take turns damaging each other
	public static void showdown(Battlez player, Battlez comp, Scanner userIn) {
		int choice;
		System.out.print(showdownMessage()+"\n==\n\n");
		
		while (true) {
			
			//displays user and computer information (name, health, critical hits)
			System.out.println(comp.getName()+"[COMPUTER]\t\t\tHealth: "+comp.getHealth()
					+"\t\tCriticals: "+comp.getCritHits()+"\n"+player.getName()+"[YOU]\t\tHealth: "+player.getHealth()+"\t\tCriticals: "
					+ player.getCritHits()+"\n");
			
				//displays the ultimate option if the user has more than 3 critical hits and hasn't used the ultimate
				if (player.checkUltimate()==true && player.getUltimateUsed()==false) {
					System.out.print("\n\n1. Attack\n2. Defend\n3. Ultimate\n0. Abandon Game\n\nOption: ");
					choice = userInput(userIn, 0, 3);
				}
				
				//default options if user does not have 3 or more critical hits
				else {
					System.out.print("\n\n1. Attack\n2. Defend\n0. Abandon Game\nOption: ");
					choice = userInput(userIn, 0, 2);
				}
				
				if (choice==0) {
					System.out.println("\n\n==========\nGAME ABANDONED\n==========\n\n");
					break;
				}
				
				//computer acts first
				player.clash(comp.actionChoice(player.getHealth(), player.getAtkDmg()), comp.getUltimateUsed(), comp.getUltimate(), comp.getReflect());
				System.out.println("\n\n"+comp.displayClashReport(player.getClashDmg(), player.getName()));
				//if the player is defeated, loop will immediately end
				if (characterRetired(player, comp)==true) {
					break;
				}
				
				//clash section
				if (choice==1) {
					comp.clash(player.attack(), player.getUltimateUsed(), player.getUltimate(), player.getReflect());
					System.out.println(player.displayClashReport(comp.getClashDmg(), comp.getName())+"\n\n");
					//if the player survives and attacks the computer, loop will end
					if (characterRetired(player, comp)==true) {
						break;
					} 
					
					
				}
				else if (choice==2) {
					comp.clash(player.defend(comp.getAtkDmg()), player.getUltimateUsed(), player.getUltimate(), player.getReflect());
					System.out.println(player.displayClashReport(comp.getClashDmg(), comp.getName())+"\n\n");
					if (characterRetired(player, comp)==true) {
						break;
					}
					
				}
				else if (choice==3) {
					comp.clash(player.useUltimate(comp.getHealth()), player.getUltimateUsed(), player.getUltimate(), player.getReflect());
					System.out.println(player.displayClashReport(comp.getClashDmg(), comp.getName())+"\n\n");
					if (characterRetired(player, comp)==true) {
						break;
					}
					
				}
		
		}
		
		//prints depending on who won
		if (player.retired()==true) {
			System.out.println("====\n"+comp.getName()+" HAS DEFEATED "+player.getName()+"... "+comp.getName()+
					" WINSS!!\n\nCOMPUTER WINS!!\n\n");
		}
		if (comp.retired()==true) {
			System.out.println("====\n"+player.getName()+" HAS DEFEATED "+comp.getName()+"... "+player.getName()+
					" WINSS!!\n\nPLAYER WINS!!\n\n");
		}
		
		System.out.println(roundStats(player, comp));//displays stats from the round
			
	}
	
	public static String roundStats(Battlez player, Battlez comp) {
		return "[YOU]"+player.displayRoundInfo(comp.getDmgTaken())+"\n\n[COMPUTER]"+comp.displayRoundInfo(player.getDmgTaken());
	}
	
	
	//checks if player has retired
	public static boolean characterRetired(Battlez player, Battlez comp) {
		if (player.retired()==true || comp.retired()==true) {
			return true;
		}	
		return false;
	}
	

	
	
	
	//general method for the users input, can be used with any methods given a min/max values
	public static int userInput(Scanner userIn, int min, int max) {
		int userInput;
		while (true) {
			try {
				userInput = userIn.nextInt();
			}
			catch(Exception e) {
				System.out.print("\nInvalid input. TRY AGAIN\nOption: ");
				userIn.nextLine();//consumes left-over new line
				continue;
			}
			if (userInput>=min && userInput<=max) {	
				break;
			}
			else {
				System.out.print("\nPick a valid option between "+min+" & "+max+"\nOption: ");
			}
		}
		return userInput;
	}

}//class end
