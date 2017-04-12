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
   newList->parType=UNKNOWN;
   newList->valType=NONE;
   return newList;
}

ListItem* createItem(char* theName,char* theValue,int theParType){
   ListItem *newItem = malloc(sizeof(ListItem));
   newItem->name = malloc(sizeof(char)*strlen(theName));
   strcpy(newItem->name,theName);
   if(theValue!=NULL){
      newItem->value = malloc(sizeof(char)*strlen(theValue));
      strcpy(newItem->value,theValue);
   }
   newItem->parType=theParType;
   newItem->valType=NONE;
   newItem->verified=FALSE;
   newItem->next=NULL;
   return newItem;
}

void addItem(ListItem *theList,char *theName,char *theValue,int theParType){
   if(theList!=NULL){
      ListItem *iter = theList;
      while(iter->next!=NULL){
         iter=iter->next;
      }
      iter->next = createItem(theName,theValue,theParType);
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
         printf("%s\n",iter->name);
         if(iter->value!=NULL){
            printf("%s\n",iter->value);
         }
         iter=iter->next;   
      }

   }
   else{
      printf("Error! List is empty!\n"); 
   } 

}

