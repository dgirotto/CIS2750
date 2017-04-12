class Foo
{
  public String getBar(int i) throws AlsCustomException
  {
    if (i < 0)
    {
      // throw our custom exception
      throw new AlsCustomException((i + " is less than zero"));
    }
    else
    {
      return "The value is greater than zero";
    }
  }
}

