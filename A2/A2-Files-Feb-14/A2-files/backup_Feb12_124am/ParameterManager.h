/* Name: Daniel Girotto
ID: 0783831 */

#include <stdio.h>
#include "ParameterList.h"

#define INT_TYPE 0
#define REAL_TYPE 1
#define BOOLEAN_TYPE 2 
#define STRING_TYPE 3
#define LIST_TYPE 4

typedef int param_t;

#define true 1
#define false 0
typedef int Boolean;

typedef union param_value{
   int int_val;
   float real_val;
   char *str_val;
   Boolean bool_val;
   ParameterList *list_val;
}unionType;

typedef struct PVal{
   struct PVal *next;
   int required;
   int parType;
   char* parName;
   union param_value value;
}ParamVal;

typedef struct{
   ParamVal *head;
}ParameterManager;

ParameterManager * PM_create(int);
int PM_destroy(ParameterManager*);
int PM_parseFrom(ParameterManager*,FILE*,char);
int PM_manage(ParameterManager*,char*,param_t,int);
int PM_hasValue(ParameterManager*,char*);	
union param_value PM_getValue(ParameterManager*,char*);

ParamVal* createNode(char*,param_t,int);
void addNode(ParamVal*,ParamVal*);
int chkExists(ParameterManager*,char*);
ParamVal* findRegType(ParameterManager*,char*);
