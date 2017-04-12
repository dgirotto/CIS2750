#include <stdio.h>
#include <stdlib.h>
#include "ParameterManager.c"

typedef union{
 int int_val;
}storevar;


int main(){
 int theid=0;
 ParameterManager * pm;
 pm = PM_create(10);
 PM_manage(pm,"name",STRING_TYPE,1);
 PM_manage(pm,"name2",STRING_TYPE,1);
 PM_manage(pm,"id2",INT_TYPE,1);
 PM_parseFrom(pm,stdin,'#');
 ParameterManager * pm2 = PM_create(5);
 PM_manage(pm2,"id",INT_TYPE,1);
 PM_parseFrom(pm2,stdin,'#');
 theid = PM_getValue(pm2,"id").int_val;
 printf("the id is: %d\n",theid);
 PM_destroy(pm);

return 0;
}
