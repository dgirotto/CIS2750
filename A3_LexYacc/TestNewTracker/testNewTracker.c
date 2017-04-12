#include <stdio.h>
#include <stdlib.h>
#include "parTrack.h"

int main(){
   ListItem * topList;
   ListItem * botList;
   topList = createTracker();
   addItem(topList,"hello",NULL,0);
   addItem(topList,"hellotoo",NULL,0);

   botList = createTracker();
   addItem(botList,"hello","something",0);
   addItem(botList,"hellotoo","anotherthing",0);

   printf("printing top list:\n");
   printList(topList);

   printf("\n\nprinting bottom list:\n");
   printList(botList);

return 0;
}
