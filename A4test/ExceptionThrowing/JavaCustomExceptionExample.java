public class JavaCustomExceptionExample
{
  public static void main(String[] args)
  {
    // create a new foo
    Foo foo = new Foo();
     
    try
    {
      // intentionally throw our custom exception by
      // calling getBar with a zero
      String bar = foo.getBar(-10);
    }
    catch (AlsCustomException e)
    {
      // print the stack trace
      e.printStackTrace();
    }
  }
}

