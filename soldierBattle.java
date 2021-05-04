/**
* A Battle program that simulates a battle between two soldiers.
*
* @author  Benjamin Cohn
*  
*/
import java.util.*;
import java.io.*;
import java.lang.*;
public class Battle {
    public class Soldier {
        String name, nation;
        double hp, strength;
        public Soldier(String nameVal, String nationVal, double hpVal, double strengthVal) {
            this.name = nameVal;
            this.nation = nationVal;
            this.hp = hpVal;
            this.strength = strengthVal;
        }
        public double advantage(Soldier other) {
            /*Found the right syntax here: 
            https://csawesome.runestone.academy/
            runestone/books/published/csawesome/
            Unit8-2DArray/topic-8-2-2D-array-loops-Day2.html*/
            String[][] advMatch = {
                {
                    "Russia",
                    "USA"
                },
                {
                    "USA",
                    "China"
                },
                {
                    "China",
                    "Russia"
                },
                {
                    "EU",
                    "EU"
                }
            };
            double advantagePoints = 1;
            for (String[] match: advMatch) {
                if (match[0].equals(this.nation) && match[1].equals(other.nation)) {
                    advantagePoints = 2.0;
                    break;
                } else if (match[0].equals(other.nation) && match[1].equals(this.nation)) {
                    advantagePoints = .5;
                    break;
                } else {
                    continue;
                }
            }
            return advantagePoints;
        }
        public void attack(Soldier other) {
            double number = Math.random();
            if (number > .5 && this.advantage(other) == 2) {
                double damage = this.advantage(other) * this.strength;
                other.hp -= damage;
                System.out.println(this.name + " did " + Double.toString(damage)
                 + " to " + other.name + ".\n The attack was super effective.");
            } else if (number > .5 && this.advantage(other) == .5) {
                double damage = this.advantage(other) * this.strength;
                other.hp -= damage;
                System.out.println(this.name + " did " + Double.toString(damage)
                 + " to " + other.name + ".\n The attack was not very effective.");
            } else if (number > .5 && this.advantage(other) == 1) {
                double damage = this.advantage(other) * this.strength;
                other.hp -= damage;
                System.out.println(this.name + " did " + Double.toString(damage)
                 + " to " + other.name + ".");
            } else {
                System.out.println(this.name+" missed.");
            }
        }
    }
    public static String[] stats(String filePath, String soldierName) {
        String soldierData = "";
        /*This link was helpful: 
        https://stackoverflow.com/questions/3983175/
        1-scanning-current-as-opposed-to-next-line-location-2-scanning-line-x-java
        This was also helpful: https://www.javatpoint.com/how-to-read-csv-file-in-java
        */
        try {
            Scanner sc = new Scanner(new File(filePath));
            while (sc.hasNext())  
            {
                String currentVal = sc.nextLine();
                if (currentVal.split(",")[1].equals(soldierName)) {
                    /*Learned about copyOfRange from here 
                    https://stackoverflow.com/questions/11001720/
                    get-only-part-of-an-array-in-java
                    This helped with the expection: 
                    https://www.w3schools.com/java/java_try_catch.asp */
                    soldierData = currentVal;
                    break;
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            System.out.println("File was not found");
        }
        return Arrays.copyOfRange(soldierData.split(","),
        1,soldierData.split(",").length);
    }
    public static void main(String[] args) throws Exception {

        Battle battle = new Battle();
        String[] dataOne = stats(args[0], args[1]);
        String[] dataTwo = stats(args[0], args[2]);
        Soldier soldierOne = battle.new Soldier(dataOne[0], dataOne[1], 
        Integer.parseInt(dataOne[2]), Integer.parseInt(dataOne[3]));
        Soldier soldierTwo = battle.new Soldier(dataTwo[0], dataTwo[1], 
        Integer.parseInt(dataTwo[2]), Integer.parseInt(dataTwo[3]));
        while (soldierOne.hp > 0.0 && soldierTwo.hp > 0.0) {
            soldierOne.attack(soldierTwo);
            Thread.sleep(1000);
            soldierTwo.attack(soldierOne);
            Thread.sleep(1000);
        }
        if (soldierOne.hp <= 0.0 && soldierTwo.hp <= 0.0) {
            System.out.println("Draw");
        } else if (soldierOne.hp <= 0.0) {
            System.out.println(soldierTwo.name + " has won.");
        } else if (soldierTwo.hp <= 0.0) {
            System.out.println(soldierOne.name + " has won.");
        }
    }
}




