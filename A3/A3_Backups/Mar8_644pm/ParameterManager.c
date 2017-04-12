/*
Name: Daniel Girotto
ID: 0783831 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ParameterManager.h"

ParameterManager* PM_create(int size){
   ParameterManager *newPM;
   newPM = malloc(sizeof(ParameterManager));
   newPM->head=malloc(sizeof(ParamVal));
   newPM->head->next=NULL;
   return newPM;
}

int PM_destroy(ParameterManager* theList){ /* return val is wrong */
   if(theList!=NULL){ /* tests if the list has been created or not */
      if(theList->head->next!=NULL){
         ParamVal* current = theList->head->next;
         ParamVal* next;
         while(current!=NULL){
            next=current->next;
            free(current->parName);
	        if(current->parType==3 && current->value.str_val!=NULL){
	           free(current->value.str_val);
	        }
	        else if(current->parType==4 && current->value.list_val!=NULL){
	           if(current->value.list_val->next!=NULL){
	              destroyList(current->value.list_val);
	           }
	           free(current->value.list_val);
	        }
            free(current);
            current=next;
         }  
      }
      else{
         printf("The ParameterManager is empty!\n");
      }
   }
   else{
      printf("A ParameterManager hasn't been created yet!\n");
   }
   return 1;
}

int PM_parseFrom(ParameterManager *p,FILE *fp,char cmt){ /* still need to FREE a few strings */
   ParamVal* toRecord;
   ParameterList* newList; /* used to store/record list values */
   char c,buffer[100]; /* not sure about size */
   int nFlag=1,vFlag=0,cFlag=0,lFlag=0,tFlag=0,sFlag=0,eListFlag=0,qCtr=0,nCtr=0,vCtr=0,lCtr=0,cmtCtr=0,specStr=0;
   char* name = NULL; /* used to record name */
   char* value = NULL; /* used to record values */
 
   if(fp==NULL){
      printf("Parse error!\n");
      return 0;
   }
   c = fgetc(fp);
   if(c==EOF){
      printf("File is empty!\n");
      return 0;
   }
   while(c!=EOF && specStr==0){ /* specStr (special string) is a flag for the "#!#" string */
      if(c==cmt && cFlag==0){ /* turn on comment flag */
         cFlag=1;  
         cmtCtr=1;
      }
      else if(c=='!' && cmtCtr==1){
         cmtCtr=2;
      }
      else if(c==cmt && cmtCtr==2){
         specStr=1;
      }
      else if(c=='\n' && cFlag==1){ /* turn off comment flag (new line is read) */
         cFlag=0;
      }
      else if(c==' ' && cFlag==0 && vFlag==1 && (sFlag==1||lFlag==1)){ /* list vals and strings must allow spaces */
         if(sFlag==1 && lFlag==0){
            buffer[vCtr]=c;
            vCtr++;
         }
         else if(lFlag==1){
            buffer[lCtr]=c;
            lCtr++;
         }
      }
      else if(c=='=' && cmt==0 && nFlag==0 && vFlag==1){
         printf("Parse error! '=' before ';'!\n");
         return 0;
      }
      else if(c==';' && cmt==0 && nFlag==1){
         printf("Parse error! ';' before '='!\n");
         return 0;
      }
      else if((c==';'||c==','||c=='}') && cmt==0 && lFlag==1 && qCtr<2 && (strlen(buffer)>0)){
         if(qCtr==0){
            printf("Parse error! List values must have quotation marks!\n");
         }
         else{
            printf("Parse error! Did not close quotation marks!\n");
         }
         qCtr=0;
         return 0;
      }
      else if(c=='=' && cFlag==0 && nFlag==1 && vFlag==0 && lFlag==0){ /* name has been found (record it) */
         nFlag=0;
         vFlag=1;
         if(name!=NULL){ 
            free(name);
         }
         name = malloc(sizeof(char)*nCtr);
         strncpy(name,buffer,nCtr);
         name[nCtr]='\0'; /* terminate the string */   
         nCtr=0; /* reset the name counter */
      }
      else if(c==';' && cFlag==0 && vFlag==1 && nFlag==0 && lFlag==0 && (qCtr==0 || qCtr==2)){ /* value has been found (record it) */
         tFlag=1;
         nFlag=1;
         vFlag=0;
         sFlag=0;
         buffer[vCtr]='\0';
 
         toRecord = findRegType(p,name); /* find the node with name "pname" so the value can be recorded */
         if(toRecord!=NULL){
            if(toRecord->parType==0){ /* store as INTEGER */
               toRecord->value.int_val=atoi(buffer);
            }
            else if(toRecord->parType==1){ /* store as FLOAT */
               toRecord->value.real_val=atof(buffer);
            }
            else if(toRecord->parType==2){ /* store as BOOLEAN */
               if(strcmp(buffer,"true")==0){
                  toRecord->value.bool_val=true;
               }
               else{
                  toRecord->value.bool_val=false;
               }  
            }    
            else if(toRecord->parType==3){ /* store as STRING */
               toRecord->value.str_val = malloc(sizeof(char)*strlen(buffer));
               strcpy(toRecord->value.str_val,buffer);
            }
            else{
               printf("ERROR! Invalid type\n");
            }
         }  
         vCtr=0; /* reset the value counter */
         qCtr=0;
      }  
      else if(c!='\n' && c!=' ' && cFlag==0 && nFlag==1){ /* record name char */
         buffer[nCtr]=c;
         nCtr++;
      }
      else if(c=='{' && cFlag==0 && vFlag==1){ /* turn on the list flag */
         newList=createList();
         buffer[0]='\0';
         lFlag=1;
      }
      else if(c=='"' && cFlag==0 && vFlag==1){ /* used to handle strings */
         if(qCtr==0){
            qCtr++;
            if(lFlag==0){
	           sFlag=1;
            }
         }   
         else if(qCtr==1){
            qCtr++;
         }
         else{ /* invalid number of quotes */
            qCtr=3;
            printf("ERROR: Too many quotes\n");
	    return 0;
         }
      }
      else if((c==','||c=='}') && cFlag==0 && vFlag==1 && lFlag==1){ /* list value node found (record it) */
   /* create a new list node with what ever is in the buffer */
         if(c=='}' && (strlen(buffer)==0)){
            eListFlag=1;
         } 
         else{
            if(qCtr!=2){
               printf("Error! Two quotes are required for each list value!\n");
	       return 0;
            }
            if(value!=NULL){
               free(value); /* free the memory associated with "value" */
	       value=NULL;
            }
            value = malloc(sizeof(char)*lCtr);
            strncpy(value,buffer,lCtr);
            value[lCtr]='\0';
            ParameterList* newListNode = createLNode(value);
   
            if(newList!=NULL){
               addLNode(newList,newListNode);
            }
            else{
               printf("ERROR. The list hasn't been created yet - can't add %s to it!\n",value);
	       return 0;
            }
            free(value);
            value=NULL;
            lCtr=0;
            qCtr=0;
         } 
      }
      else if(c==';' && cFlag==0 && vFlag==1 && lFlag==1){ /* the end of a list value */
         toRecord = findRegType(p,name);
         if(toRecord!=NULL){
            toRecord->value.list_val = newList;
         }
         lFlag=0; /* turn off the list flag */
         vFlag=0;
         nFlag=1;
         tFlag=1;
         eListFlag=0;
      }
      else if(c!='\n' && c!=' ' && cFlag==0 && vFlag==1 && lFlag==1){ /* record list char */
         buffer[lCtr]=c;
         lCtr++;
      }
      else if(c!='\n' && c!=' ' && cFlag==0 && vFlag==1 && lFlag==0){ /* record value char */
         buffer[vCtr]=c;
         vCtr++;
      }
      c = fgetc(fp);	
   }  
   if(nFlag==1 && tFlag==0|| vFlag==1){
      printf("nFlag : %d\nvFlag : %d\ntFlag: %d\n",nFlag,vFlag,tFlag);
      return 0;
   }
   if(specStr==1){
      printf("found #!#");
   }
   return 1; /* 1 = success. 0 = parse error */
}

int PM_manage(ParameterManager *p,char* pname,param_t ptype,int required){
   if(chkExists(p,pname)==0){ /* the parameter name doesn't exist yet; you may register it */
      ParamVal* newNode = createNode(pname,ptype,required);
      addNode(p->head,newNode);
   }
   else{
      printf("ERROR. The parameter \"%s\" already exists. Did not register \"%s\"!\n",pname,pname);
      return 0;
   }
   return 1;
}

int PM_hasValue(ParameterManager *p, char *pname){
   ParamVal *iter = p->head;
   if(iter->next!=NULL){
      iter=iter->next;
      while(strcmp(iter->parName,pname)!=0){
         if(iter->next!=NULL){
            iter=iter->next;
         } 
         else{
            return 0; /* "pname" doesn't exist in p */
         }
      }
      if(iter->value.str_val!=0){ /* tests if there is anything at memory "str_val" */
         return 1; /* pname has a value */
      }
      else{
         return 0; /* pname doesn't have a value */
      }
   }  
   else{
      printf("ERROR. The list is empty!\n");
      return 0;
   }
}

union param_value PM_getValue(ParameterManager *p, char *pname){
   ParamVal *iter = p->head;
   if(PM_hasValue(p,pname)==1){
      iter=iter->next;
      while(strcmp(iter->parName,pname)!=0){ /* while loop exits once the node is found */
         iter=iter->next;
      }
   }
   return iter->value;
}

ParamVal* createNode(char* pname,param_t ptype,int required){
   ParamVal* newNode = malloc(sizeof(ParamVal));
   newNode->parName = malloc(sizeof(char)*strlen(pname));
   strcpy(newNode->parName,pname);
   newNode->parType=ptype;
   newNode->required=required;
   newNode->next=NULL;
   return newNode;
}

void addNode(ParamVal* head,ParamVal* newNode){
   ParamVal* iter = head;
   if(head!=NULL){
      while(iter->next!=NULL){
         iter=iter->next; 
      }
      iter->next=newNode;
   }
   else{
      printf("ERROR! A list does not exist\n");
   }
}

int chkExists(ParameterManager *thePM,char* pname){ /* if a parameter in the list already exists, return 1; else return 0 */
   ParamVal* iter=thePM->head->next;
   while(iter!=NULL){
      if(strcmp(iter->parName,pname)==0){ /* parameter already exists! */
         return 1;
      }
      iter=iter->next; /* move to the next parameter in the list */
   }
   return 0;
}

ParamVal* findRegType(ParameterManager* p,char* pname){
   ParamVal* theNode = p->head;
   if(theNode->next!=NULL){
      theNode=theNode->next;
      while(strcmp(theNode->parName,pname)!=0 && theNode->next!=NULL){
         theNode=theNode->next;  
      }
      if(strcmp(theNode->parName,pname)!=0){
         printf("ERROR! %s was never registered with PM_manage!\n",pname);
         theNode=NULL;
      }
   }  
   else{
      printf("ERROR! The register parameter list is empty!\n");
      theNode=NULL;
   }
   return theNode; /* the node containing the proper param name (if not null)*/
}

