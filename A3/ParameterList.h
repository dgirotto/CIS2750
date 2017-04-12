/* Name: Daniel Girotto
ID: 0783831 */

typedef struct ParameterList{
   struct ParameterList* next;
   struct ParameterList* currItem; /* the current item the list is pointing to (used for iterative purposes) */
   char* data;
}ParameterList;

ParameterList* createList();
void destroyList(ParameterList*);
ParameterList* createLNode(char*);
void addLNode(ParameterList*,ParameterList*);
char* PL_next(ParameterList*);

