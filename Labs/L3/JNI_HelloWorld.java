public class JNI_HelloWorld{
   static { System.loadLibrary("JNI_HelloWorld");}
   private native void printMsg();

   public static void main(String[] args){
      JNI_HelloWorld newobj = new JNI_HelloWorld();
      newobj.printMsg();
   }
}
