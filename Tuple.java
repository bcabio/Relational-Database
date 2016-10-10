import java.util.*;

public class Tuple {

  private ArrayList<String> attributes; //column name
  private ArrayList<String> domains; //column type
  private ArrayList<Comparable> tuple; //column content

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

  public Tuple project(ArrayList<String> cnames){
    ArrayList<String> attr = cnames;
    ArrayList<String> doms = new ArrayList<String>();
    for(int i = 0; i < cnames.size(); i++){
      doms.add(this.attributes.get(this.attributes.indexOf(cnames.get(i))));
    }
    Tuple tempTuple = new Tuple(doms, attr);
    for(int i = 0; i < cnames.size(); i++){
      tempTuple.tuple.add(this.tuple.get(this.attributes.indexOf(cnames.get(i))));
    }
    return tempTuple;
  }


  //sorry for the ridiculously ugly code. I wasn't allowed to
  //add extra methods for the nested switch cases
  public boolean select(String lopType, String lopValue, String comparison, String ropType, String ropValue) {
    String columnName, types = lopType + ropType, s, s2, leftOperandString, rightOperandString;
    String leftColumn, rightColumn;
    Double leftOperandNumber, rightOperandNumber, d, d2;
    int i;
    switch(types) {
      case "numnum":  leftOperandNumber = Double.parseDouble(lopValue);
                      rightOperandNumber = Double.parseDouble(ropValue);
                      switch(comparison) {
                        case "<": return leftOperandNumber < rightOperandNumber;
                        case ">": return leftOperandNumber > rightOperandNumber;
                        case ">=":return leftOperandNumber >= rightOperandNumber;
                        case "<=":return leftOperandNumber <= rightOperandNumber;
                        case "=": return leftOperandNumber == rightOperandNumber;
                        case "<>":return leftOperandNumber != rightOperandNumber;
                        default: return false;
                      }
      case "strstr":  leftOperandString = lopValue;
                      rightOperandString = ropValue;
                      switch(comparison) {
                        case "<": return false;
                        case ">": return false;
                        case ">=":return false;
                        case "<=":return false;
                        case "=": return leftOperandString.equals(rightOperandString);
                        case "<>":return !leftOperandString.equals(rightOperandString);
                        default: return false;
                      }
      case "colnum":  rightOperandNumber = Double.parseDouble(ropValue);
                      columnName = lopValue;
                      if(this.domains.get(this.attributes.indexOf(columnName)) == "DECIMAL"){
                        d = (Double)(this.tuple.get(this.attributes.indexOf(columnName)));
                        switch(comparison) {
                          case "<": return d < rightOperandNumber;
                          case ">": return d > rightOperandNumber;
                          case ">=":return d >= rightOperandNumber;
                          case "<=":return d <= rightOperandNumber;
                          case "=": return d == rightOperandNumber;
                          case "<>":return d != rightOperandNumber;
                          default: return false;
                        }
                      } else {
                        i = (Integer)(this.tuple.get(this.attributes.indexOf(columnName)));
                        switch(comparison) {
                          case "<": return i < rightOperandNumber;
                          case ">": return i > rightOperandNumber;
                          case ">=":return i >= rightOperandNumber;
                          case "<=":return i <= rightOperandNumber;
                          case "=": return i == rightOperandNumber;
                          case "<>":return i != rightOperandNumber;
                          default: return false;
                        }
                      }
      case "colstr":  rightOperandString = ropValue;
                      columnName = lopValue;
                      s = (String)(this.tuple.get(this.attributes.indexOf(columnName)));
                      switch(comparison) {
                        case "<": return false;
                        case ">": return false;
                        case ">=":return false;
                        case "<=":return false;
                        case "=": return s.equals(rightOperandString);
                        case "<>":return !s.equals(rightOperandString);
                        default: return false;
                      }
      case "numcol":  leftOperandNumber = Double.parseDouble(lopValue);
                      columnName = ropValue;
                      if(this.domains.get(this.attributes.indexOf(columnName)) == "DECIMAL"){
                        d = (Double)(this.tuple.get(this.attributes.indexOf(columnName)));
                        switch(comparison) {
                          case "<": return leftOperandNumber < d;
                          case ">": return leftOperandNumber > d;
                          case ">=":return leftOperandNumber >= d;
                          case "<=":return leftOperandNumber <= d;
                          case "=": return leftOperandNumber == d;
                          case "<>":return leftOperandNumber != d;
                          default: return false;
                        }
                      } else {
                        i = (Integer)(this.tuple.get(this.attributes.indexOf(columnName)));
                        switch(comparison) {
                          case "<": return leftOperandNumber < i;
                          case ">": return leftOperandNumber > i;
                          case ">=":return leftOperandNumber >= i;
                          case "<=":return leftOperandNumber <= i;
                          case "=": return leftOperandNumber == i;
                          case "<>":return leftOperandNumber != i;
                          default: return false;
                        }
                      }
      case "strcol":  leftOperandString = lopValue;
                      columnName = ropValue;
                      s = (String)(this.tuple.get(this.attributes.indexOf(columnName)));
                      switch(comparison) {
                        case "<": return false;
                        case ">": return false;
                        case ">=":return false;
                        case "<=":return false;
                        case "=": return leftOperandString.equals(s);
                        case "<>":return !leftOperandString.equals(s);
                        default: return false;
                      }
      case "colcol":    rightColumn = (String)(this.tuple.get(this.attributes.indexOf(ropValue)));
                        leftColumn = (String)(this.tuple.get(this.attributes.indexOf(lopValue)));
                        switch(comparison) {
                          case "<": return false;
                          case ">": return false;
                          case ">=":return false;
                          case "<=":return false;
                          case "=": return rightColumn.equals(leftColumn);
                          case "<>":return !rightColumn.equals(leftColumn);
                          default: return false;
                        }

      default: return false;
    }


  }


}
