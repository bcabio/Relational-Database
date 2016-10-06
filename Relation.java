import java.io.*;
import java.util.*;

public class Relation {
  // Name of the relation. Table Name
  private String name;

  // Attribute names for the relation. Table headers
  private ArrayList<String> attributes;

  // Domain classes or types of attributes; possible values: INTEGER, DECIMAL, VARCHAR. Data types
  private ArrayList<String> domains;

  // Actual data storage (list of tuples) for the relation. Rows
  private ArrayList<Tuple> table;

  // METHODS

  // Constructor; set instance variables
  public Relation (String name, ArrayList<String> attributes, ArrayList<String> domains) {
    this.attributes = attributes;
    this.name = name;
    this.domains = domains;
    table = new ArrayList<Tuple>();
  };

  // returns true if attribute with name aname exists in relation schema
  // false otherwise
  public boolean attributeExists(String aname) {
    return attributes.contains(aname);
  }

  // returns domain type of attribute aname; return null if not present
  public String attributeType(String aname) {
    if(attributes.contains(aname)){
      return domains.get(attributes.indexOf(aname));
    } else {
      return null;
    }
  }


  // Print relational schema to screen.
  public void displaySchema() {
    //System.out.print(attributes.size());
    for(int i = 0; i < attributes.size(); i++){
      System.out.print(attributes.get(i) + ":" + domains.get(i));
      if(i != attributes.size()-1)
        System.out.print(",");
    }
  }

  // Set name of relation to rname
  public void setName(String rname) {
    this.name = rname;
  }

  // Add tuple tup to relation; Duplicates are fine.
  public void addTuple(Tuple tup) {
    table.add(tup);
  }

  // Print relation to screen (see output of run for formatting)
  public void displayRelation() {
    System.out.print(name + "(");
    displaySchema();
    System.out.println(":" + attributes.size());
    //System.out.print("------");
    System.out.print(")");
    System.out.println("Number of tuples: " + table.size());
    System.out.println();
    for(int i = 0; i < table.size(); i++){
      System.out.println(table.get(i).toString());
    }
  }

  // Return String version of relation; See output of run for format.
  public String toString() {
    String s = "";
    s += name + "(";
  //System.out.println(this.attributes.size());
    for(int w = 0; w < attributes.size(); w++){
      s += attributes.get(w) + ":" + domains.get(w);
      //System.out.println(attributes.get(w) + ":" + domains.get(w));
      if(w != attributes.size()-1){
        s+=",";
      }
    }

    s += ")\n";
    s+= "Number of tuples: " + table.size() + "\n\n";
    for(int i = 0; i < table.size(); i++){
      s+= table.get(i).toString();
    }
    return s;
  }

  //Remove duplicate tuples from this relation
  public void removeDuplicates(){
    for(int i = 0; i < this.table.size(); i++){
      for(int j = i+1; j < this.table.size(); j++){
        if(this.table.get(i).equals(this.table.get(j))){
          this.table.remove(j);
          j--;
          }
        }
      }
    }

    public boolean member(Tuple t){
      for(int i = 0; i < this.table.size(); i++){
        if(t.equals(this.table.get(i))){
          return true;
        }
      }
      return false;
    }

    public Relation union(Relation r2){
      ArrayList<String> tempDomains = this.domains;
      ArrayList<String> tempAttributes = this.attributes;
      ArrayList<Tuple> tempTable = new ArrayList<Tuple>();

      //iterate over the first relation and add clone
      //of tuple to the tempTable
       for(int w = 0; w < this.table.size(); w++){
        tempTable.add(this.table.get(w).clone(this.attributes));
      }

      //iterate over the second relation and add clone of
      //tuple to the tempTable
      for(int w = 0; w < r2.table.size(); w++){
       tempTable.add(r2.table.get(w).clone(this.attributes));
     }

     //Name of the new table
      String unionName = this.name + "_UNION_" + r2.name;

      //calling relation
      Relation tempRelation = new Relation(unionName, tempAttributes,tempDomains);

      //add tuples to relation
      for(int k = 0; k < tempTable.size(); k++){
        tempRelation.addTuple(tempTable.get(k));
      }
      tempRelation.removeDuplicates();
      return tempRelation;
    }

    public Relation intersect(Relation r2){
      ArrayList<String> tempDomains = this.domains;
      ArrayList<String> tempAttributes = this.attributes;
      ArrayList<Tuple> tempTable = new ArrayList<Tuple>();

      //iterate over the first relation and add clone
      //of tuple to the tempTable
       for(int r1Counter = 0; r1Counter < this.table.size(); r1Counter++){
         for(int r2Counter = 0; r2Counter < r2.table.size(); r2Counter++){
           if(this.table.get(r1Counter).equals(r2.table.get(r2Counter)))
            tempTable.add(this.table.get(r1Counter).clone(this.attributes));
         }
      }

     //Name of the new table
      String unionName = this.name + "_INTERSECT_" + r2.name;

      //calling relation
      Relation tempRelation = new Relation(unionName, tempAttributes,tempDomains);

      //add tuples to relation
      for(int k = 0; k < tempTable.size(); k++){
        tempRelation.addTuple(tempTable.get(k));
      }
      tempRelation.removeDuplicates();
      return tempRelation;
    }

    public Relation minus(Relation r2){
      ArrayList<String> tempDomains = this.domains;
      ArrayList<String> tempAttributes = this.attributes;
      ArrayList<Tuple> tempTable = new ArrayList<Tuple>();

      //iterate over the first relation and add clone
      //of tuple to the tempTable

      for(int i = 0; i < this.table.size(); i++){
        tempTable.add(this.table.get(i));
      }

      for(int r1Counter = 0; r1Counter < this.table.size(); r1Counter++){
        for(int r2Counter = 0; r2Counter < r2.table.size(); r2Counter++){
          System.out.println(r1Counter + ":" + r2Counter);
          System.out.println("tempTable" + tempTable.size());
          if(this.table.get(r1Counter).equals(r2.table.get(r2Counter))){
           tempTable.remove(this.table.get(r1Counter));
         }
        }
      }

      // for(int tableSetup = 0; tableSetup < this.table.size(); tableSetup++){
      //   if(){
      //     tempTable.remove(tableSetup);
      //     // tempTable.remove(r2.table.get(tableSetup).clone(this.attributes));
      //     // System.out.println("" + r2.table.get(tableSetup));
      //     // System.out.println(tempTable);
      //     // System.out.println(tempTable.indexOf(r2.table.get(tableSetup)));
      //   }
      //     //tempTable.remove(tempTable.indexOf(r2.table.get(tableSetup)));
      // }

      // for(int tableSetup = 0; tableSetup < this.table.size(); tableSetup++){
      //   tempTable.add(this.table.get(tableSetup).clone(this.attributes));
      // }
      //
      //  for(int r1Counter = 0; r1Counter < this.table.size(); r1Counter++){
      //    for(int r2Counter = r1Counter; r2Counter < r2.table.size(); r2Counter++){
      //      System.out.println(r1Counter + ":" + r2Counter);
      //      if(this.table.get(r1Counter).equals(r2.table.get(r2Counter))){
      //       System.out.println(tempTable.get(r1Counter));
      //       tempTable.remove(r1Counter);
      //     }
      //       System.out.println(this.table.get(r1Counter));
      //       System.out.println(r2.table.get(r2Counter));
      //       //System.out.println(tempTable);
      //       System.out.println("++++++++");
      //    }
      // }
      // Relation intersectRelation = this.intersect(r2);
      // Relation finalRelation = this.intersect(intersectRelation);
      // finalRelation.setName("REL1_MINUS_REL2");
     //Name of the new table
      String unionName = this.name + "_MINUS_" + r2.name;

      //calling relation
      Relation tempRelation = new Relation(unionName, tempAttributes,tempDomains);

      //add tuples to relation
      for(int k = 0; k < tempTable.size(); k++){
        tempRelation.addTuple(tempTable.get(k));
      }
      tempRelation.removeDuplicates();
      return tempRelation;
    }

    public Relation rename(ArrayList<String> cnames){
      ArrayList<String> attrs = new ArrayList<String>();
      ArrayList<String> doms = new ArrayList<String>();
      for(int i = 0; i < cnames.size(); i++){
        attrs.add(cnames.get(i));
      }

      for(int j = 0; j < this.domains.size(); j++){
        doms.add(this.domains.get(j));
      }

      Relation newRel = new Relation("rel", attrs, doms);
      for(int k = 0; k < this.table.size(); k++){
        newRel.addTuple(this.table.get(k).clone(this.attributes));
      }

      return newRel;

    }

    public Relation times(Relation r2){
      ArrayList<String> attrs = new ArrayList<String>();
      ArrayList<String> doms = new ArrayList<String>();
      for(int i = 0 ; i < this.attributes.size(); i++){
        if(this.attributes.indexOf(r2.attributes.get(i)) != -1)
          attrs.add(this.name + "." + this.attributes.get(i));
         else
        attrs.add(this.attributes.get(i));

        doms.add(this.domains.get(i));
      }

      for(int i = 0 ; i < this.attributes.size(); i++){
        if(this.attributes.indexOf(r2.attributes.get(i)) != -1)
          attrs.add(r2.name + "." + r2.attributes.get(i));
         else
        attrs.add(this.attributes.get(i));

        doms.add(r2.domains.get(i));
      }
      String name = "";
      String relName = this.name + "TIMES" + r2.name;
      Relation newRel = new Relation(relName, attrs, doms);

      for(int i = 0; i < this.table.size(); i++){
        for(int j = 0; j < r2.table.size(); j++){
          newRel.addTuple(this.table.get(i).concatenate(r2.table.get(j),attrs, doms));
        }
      }

      return newRel;

  }

  public Relation project(ArrayList<String> cnames){
    ArrayList<String> attr = cnames;
    System.out.println(cnames);
    ArrayList<String> doms = new ArrayList<String>();

    for(int i = 0; i < cnames.size(); i++){
      doms.add(this.domains.get(this.attributes.indexOf(cnames.get(i))));
    }
    String name = "PROJECT";
    for(int i = 0; i < cnames.size(); i++){
      name += "_";
      name += cnames.get(i);
    }
    Relation rel = new Relation(name, attr, doms);

    for(Tuple t : this.table){
      rel.table.add(t.project(cnames));
    //  System.out.println(rel.table);
    }
    rel.removeDuplicates();
    return rel;
  }


}
