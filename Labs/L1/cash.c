#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(){
 char theChar;
 int flag=0;
 while(theChar = getchar()){
  if(theChar==EOF){
   exit(0);
  }
  if(theChar=='$'){
   putchar(theChar);
   flag=1;
   while(flag==1){
    theChar = getchar();
    if(!(theChar-48>=0 && theChar-48<10 && flag==1)){
     flag=0;
     putchar(theChar);
    }
    if(theChar=='$'){
     flag=1;
    }
    if(theChar == EOF){
     exit(0);
    }
   }
  }
  else{
   putchar(theChar);
  }
 }

return 0;
}
