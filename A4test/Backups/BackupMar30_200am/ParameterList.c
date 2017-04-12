/* Name: Daniel Girotto
ID: 0783831 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ParameterList.h"

ParameterList* createList(){
   ParameterList* newList = malloc(sizeof(ParameterList));
   newList->next = NULL;
   newList->currItem = NULL;
   newList->data = NULL;
   return newList;
}

void destroyList(ParameterList* theList){
   if(theList->next!=NULL){
      ParameterList* current = theList->next;
      ParameterList* next;
      while(current!=NULL){
         next=current->next;
         free(current->data);
         free(current);
         current=next;  
      }
   } 
}

ParameterList* createLNode(char* data){
   ParameterList* newListNode = malloc(sizeof(ParameterList));
   newListNode->data = malloc(sizeof(char)*strlen(data));
   strcpy(newListNode->data,data);
   newListNode->next=NULL;
   newListNode->currItem=NULL;
   return newListNode;
}

void addLNode(ParameterList* head,ParameterList* toAdd){
   ParameterList* iter = head;
   while(iter->next!=NULL){
      iter = iter->next;  
   }
   iter->next = toAdd;
}

char* PL_next(ParameterList *theList){ /* returns NULL if the list is empty*/
   ParameterList *iter = theList;
   if(iter==NULL){ /* there is no list */
      printf("Error! No list created!\n");
      return NULL;
   }
   else{ /* a list exists */
      if(iter->currItem!=NULL){ /* whether or not currItem points to something */
         if(iter->currItem->next!=NULL){ /* if an item exists after currItem (haven't reached the end of the list yet) */
            iter->currItem = iter->currItem->next; /* point to the next item in the list */
         }
         else{ /* we've reached the end of the list */
            return NULL;   
         }
      }
      else{ /* if currItem doesn't point to anything */
         if(iter->next==NULL){ /* if a list item does not exist in the list */
            printf("Error! The list is empty!\n");
            return NULL;
         }
         else{ /* at least one list item exists in the list */
            iter->currItem=iter->next; /* currItem points to the first list item in the list */ 
         }
      }
   }
   return iter->currItem->data;
}

