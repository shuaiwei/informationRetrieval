import java.util.Scanner;
import java.util.InputMismatchException;

class search{
     public static void main(String args[]){

        Scanner scanner = new Scanner(System.in);
        int typeInt;
        String term;
        while(true){
            System.out.println("Enter search type, 1:uniword, 2:biword");
            System.out.format(">");

            while(true){
                try{
                    typeInt = scanner.nextInt();
                    scanner.nextLine(); 
                    if(typeInt == 1){
                        System.out.println("You have select uniword search way!");
                        break;
                    }
                    else if (typeInt == 2){
                        System.out.println("You have select biword search way!");
                        break;
                    }
                    else{
                        System.out.println("Input error");
                        System.out.println("Enter search type, 1:uniword, 2:biword");
                        System.out.format(">");
                    }
                }catch (InputMismatchException exception) { 
                    System.out.println("Integers only, please.");
                    System.out.println("Enter search type, 1:uniword, 2:biword");
                    System.out.format(">"); 
                    scanner.nextLine();
                } 

            }

            if(typeInt == 1){
                while(true){
                    System.out.println("Now enter one word:");
                    System.out.format(">");
                    term = scanner.nextLine();
                    String [] termSplit = term.split("\\+");
                    if(termSplit.length != 1  ){
                        System.out.println("Input error, please enter just one word");     
                    }
                    else{
                        System.out.format("You have entered:%s\n",term);   
                        //Process cmdProc = Runtime.getRuntime().exec(new String[]{"ls"});
                        //Process p = Runtime.getRuntime().exec(new String[]{"bash","-c","ls ./"});
                        break;
                    }
                }
            }
            if(typeInt == 2){
                while(true){
                    System.out.println("Now enter two words:");
                    System.out.format(">");
                    term = scanner.nextLine();
                    String [] termSplit = term.split("\\s");
                    if(termSplit.length != 2 ){
                        System.out.println("Input error, please enter two words seperated by blank space");     
                    }
                    else{
                        System.out.format("You have entered:%s\n",term);
                             
                        break;
                    }
                }
            }  
        }
    }
}