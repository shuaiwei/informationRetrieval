import java.io.File;
import java.io.IOException;
import java.lang.InterruptedException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.InputMismatchException;


class palmettoSearch{
    static public boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }
	public static void main(String[] args) throws IOException,InterruptedException{
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
 
						File file = new File("./typeInt");

						// if file doesnt exists, then create it
						if (!file.exists()) {
							file.createNewFile();
						}

						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(Integer.toString(typeInt));
						bw.close();
                        break;
                    }
                    else if (typeInt == 2){
                        System.out.println("You have select biword search way!");

                        File file = new File("./typeInt");

						// if file doesnt exists, then create it
						if (!file.exists()) {
							file.createNewFile();
						}

						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
                        bw.write(Integer.toString(typeInt));
						bw.close();

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
                    String [] termSplit = term.split("\\s+");
                    if(termSplit.length != 1  ){
                        System.out.println("Input error, please enter just one word");     
                    }
                    else{
                        System.out.format("You have entered:%s\n",term);

                        File file = new File("./term");

						// if file doesnt exists, then create it
						if (!file.exists()) {
							file.createNewFile();
						}

						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(term);
						bw.close();

						Process p = Runtime.getRuntime().exec("qsub runHadoop.pbs");
						File f = new File("./output/part-r-00000");
						System.out.println("Searching is on...");
						while(!f.exists()) { 
							;
						}	
						BufferedReader in = new BufferedReader(new FileReader("./output/part-r-00000"));
						String line;
						while((line = in.readLine()) != null){
						    System.out.println(line);
						}

						in.close();
                        File output = new File("./output");
                        deleteDirectory(output);
                        break;
                    }
                }
            }
            if(typeInt == 2){
                while(true){
                    System.out.println("Now enter two words:");
                    System.out.format(">");
                    term = scanner.nextLine();
                    String [] termSplit = term.split("\\s+");
                    if(termSplit.length != 2 ){
                        System.out.println("Input error, please enter two words seperated by blank space");     
                    }
                    else{

                        System.out.format("You have entered:%s\n",term);

                        File file = new File("./term");

						// if file doesnt exists, then create it
						if (!file.exists()) {
							file.createNewFile();
						}

						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						bw.write(term);
						bw.close();

						Process p = Runtime.getRuntime().exec("qsub runHadoop.pbs");
						File f = new File("./output/part-r-00000");
						System.out.println("Searching is on...");
						while(!f.exists()) { 
							;
						}	
						BufferedReader in = new BufferedReader(new FileReader("./output/part-r-00000"));
						String line;
						while((line = in.readLine()) != null){
						    System.out.println(line);
						}

						in.close();
                        File output = new File("./output");
                        deleteDirectory(output);
                        break;
                    }
                }
            }  
        }
		
	}

}