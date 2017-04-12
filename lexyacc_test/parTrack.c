#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "parTrack.h"

ListItem* createTracker(){
   ListItem* newList = malloc(sizeof(ListItem));
   newList->next=NULL;
   newList->value=NULL;
   return newList;
}

ListItem* createItem(char* theValue){
   ListItem *newItem = malloc(sizeof(ListItem));
   newItem->value = malloc(sizeof(char)*strlen(theValue));
   strcpy(newItem->value,theValue);
   return newItem;
}

void addItem(ListItem *theList,char *theValue){
   if(theList != NULL){
      ListItem *iter = theList;
      while(iter->next!=NULL){
         iter=iter->next;
      }
      iter->next = createItem(theValue);
   }
   else{
      printf("ERROR. No list exists!\n");
   }   

}

void printList(ListItem * theList){
   ListItem * iter;
   if(theList!=NULL && theList->next!=NULL){
      iter = theList->next;
      while(iter!=NULL){
         printf("%s\n",iter->value);
         iter=iter->next;   
      }

   }
   else{
      printf("Error! List is empty!\n"); 
   } 

}
