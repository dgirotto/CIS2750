#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "parTrack.h"

ListItem* createTracker(){
   ListItem* newList = malloc(sizeof(ListItem));
   newList->next=NULL;
   newList->name=NULL;
   newList->value=NULL;
   newList->verified=FALSE;
   return newList;
}

ListItem* createItem(char* theValue,char* theName,int theType){
   ListItem *newItem = malloc(sizeof(ListItem));
   newItem->value = malloc(sizeof(char)*strlen(theValue));
   strcpy(newItem->value,theValue);
   if(theName!=NULL){
      newItem->name = malloc(sizeof(char)*strlen(theName));
   }
   newItem->type=theType;
   newItem->verified=FALSE;
   return newItem;
}

void addItem(ListItem *theList,char *theValue,int theType){
   if(theList!=NULL){
      ListItem *iter = theList;
      while(iter->next!=NULL){
         iter=iter->next;
      }
      iter->next = createItem(theValue,theType);
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
