import java.io.*;
import java.util.*;

public class Database {

  private Map<String,Relation> relations;

  // METHODS

  // Constructor; creates the empty HashMap object
  public Database() {
    relations = new HashMap<String, Relation>();
  }

  // Add relation r with name rname to HashMap
  // if relation does not already exists.
  // Make sure to set the name within r to rname.
  // return true on successful add; false otherwise
  public boolean addRelation(String rname, Relation r) {
    if(relations.get(rname) == null){
      relations.put(rname, r);
      return true;
    } else {
    return false;
    }
  }
  // Delete relation with name rname from HashMap
  // if relation exists. return true on successful delete; false otherwise
  public boolean deleteRelation(String rname) {
    if(relations.get(rname) != null){
      relations.remove(rname);
      return true;
    } else {
    return false;
    }
  }
  // Return true if relation with name rname exists in HashMap
  // false otherwise
  public boolean relationExists(String rname) {
    return (relations.get(rname) == null);
  }

  // Retrieve and return relation with name rname from HashMap;
  // return null if it does not exist.
  public Relation getRelation (String rname) {
    return relations.get(rname);
  }

  // Print database schema to screen.
  public void displaySchema() {
    Relation r;
    for(int i = 0; i < relations.keySet().toArray().length; i++){
      System.out.print(relations.keySet().toArray()[i] + "");
      System.out.print("(");
      r = relations.get(relations.keySet().toArray()[i]);
      //System.out.println("&7777&&");
      //System.out.println("787777");
      r.displaySchema();
      System.out.print(")\n");
    }
    System.out.println();
  }

  //Create the database object by reading data from several files in directory dir
  public void initializeDatabase(String dir){
    FileInputStream fin1 = null;
    BufferedReader read = null;

    try {
      fin1 = new FileInputStream(dir + "/catalog.dat");
      read = new BufferedReader(new InputStreamReader(fin1));

      String s = read.readLine();
      int totalRelations = Integer.parseInt(s);

      for(int i = 0; i < totalRelations; i++){
        ArrayList<String> attributes = new ArrayList<String>();
        ArrayList<String> domains = new ArrayList<String>();

        String relationName = read.readLine();
        int numberOfColumns = Integer.parseInt(read.readLine());
        for(int j = 0; j < numberOfColumns; j++){
          attributes.add(read.readLine());
          domains.add(read.readLine());
        }
        Relation tempRel = new Relation(relationName, attributes, domains);
        FileInputStream fin2 = new FileInputStream(dir + "/" + relationName + ".dat");
        BufferedReader buff = new BufferedReader(new InputStreamReader(fin2));
        int numberOfRows = Integer.parseInt(buff.readLine());

        for(int b = 0; b < numberOfRows; b++){
          Tuple tuple = new Tuple(attributes, domains);
          for(int n = 0; n < attributes.size(); n++){
            if(domains.get(n).equals("INTEGER"))
              tuple.addIntegerComponent(Integer.parseInt(buff.readLine()));
                else if(domains.get(n).equals("VARCHAR"))
                  tuple.addStringComponent(buff.readLine());
                  else if(domains.get(n).equals("DECIMAL"))
                    tuple.addDoubleComponent(Double.parseDouble(buff.readLine()));
                      else System.out.println("Error");
          }
          tempRel.addTuple(tuple);
        }
        fin2.close();
        addRelation(relationName, tempRel);



        }

      fin1.close();
    } catch (IOException e) {
        System.out.println("Error reading file");
      }
    }

}
