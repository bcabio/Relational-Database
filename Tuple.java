import java.util.*;

public class Tuple {

  private ArrayList<String> attributes;
  private ArrayList<String> domains;
  private ArrayList<Comparable> tuple;

  // METHODS

  // Constructor; set instance variables
  public Tuple (ArrayList<String> attr, ArrayList<String> dom) {
    attributes = attr;
    domains = dom;
    tuple = new ArrayList<Comparable>();
  }

  // Add String s at the end of the tuple
  public void addStringComponent(String s) {
    tuple.add(s);
  }

  // Add Double d at the end of the tuple
  public void addDoubleComponent(Double d) {
    tuple.add(d);
  }

  // Add Integer i at the end of the tuple
  public void addIntegerComponent(Integer i) {
    tuple.add(i);
  }

  // return String representation of tuple; See output of run for format.
  public String toString() {
    String s = "";
    int rowNumber = 0;
    for(Comparable t: tuple){
      s += t + ":";
    }
    s+="\n";
    return s;
  }

  // Return true if this tuple is equal to compareTuple; false otherwise
  public boolean equals(Tuple compareTuple){
    if(this.tuple.size() != compareTuple.tuple.size()){
      return false;

    } else{
      for(int i = 0; i < this.tuple.size(); i++){
        if(!this.tuple.get(i).equals(compareTuple.tuple.get(i))){
          // System.out.println(this.tuple);
          // System.out.println();
          // System.out.println(compareTuple.tuple);
        //  System.out.println("gotii");
          return false;
        }
      }
    }
    return true;
  }
  //Week 4
  public Tuple clone(ArrayList<String> attr){
    ArrayList<Comparable> duplicatedTuple = new ArrayList<Comparable>();
    ArrayList<String> duplicatedDomain = new ArrayList<String>();
    for(int i = 0; i < this.tuple.size(); i++){
      switch(this.domains.get(i)){
        case "INTEGER":   duplicatedDomain.add("INTEGER");
                          duplicatedTuple.add((Integer)this.tuple.get(i));
                          //System.out.println("Int");
                          break;

        case "VARCHAR":   duplicatedDomain.add("VARCHAR");
                          duplicatedTuple.add((String)this.tuple.get(i));
                          //System.out.println("String");
                          break;

        case "DECIMAL":   duplicatedDomain.add("DECIMAL");
                          duplicatedTuple.add((Double)this.tuple.get(i));
                          //System.out.println("Double");
                          break;

      }
    }
    Tuple t = new Tuple(attr,duplicatedDomain);
    t.tuple = duplicatedTuple;
    //System.out.println(t);
    return t;
  }

  public Tuple concatenate(Tuple t2, ArrayList<String> attrs, ArrayList<String> doms){
    Tuple tempTuple = new Tuple(attrs, doms);
    Tuple thisTuple = this.clone(this.attributes);
    for(int i = 0; i < this.tuple.size(); i++){
      tempTuple.tuple.add(this.tuple.get(i));
    }

    Tuple t2Tuple = t2.clone(t2.attributes);
    for(int j = 0; j < t2.tuple.size(); j++){
      tempTuple.tuple.add(t2.tuple.get(j));
    }
    System.out.println(tempTuple);
    return tempTuple;
  }

}
