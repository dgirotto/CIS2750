public class FirstClass{
  
  static{
    System.loadLibrary("FirstClass");
  }  
  native int add(int a,int b);
  native int strlength(String myString);
  public static void main(String[] args){
    String newString = new String("testing"); 
    int a=10,b=5;
    FirstClass obj = new FirstClass();
    System.out.println(obj.add(a,b));
    System.out.println(obj.strlength(newString));
  }



}
