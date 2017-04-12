typedef struct ListItem{
   struct ListItem * next;
   char* value;
}ListItem;

ListItem* createTracker(void);
ListItem* createItem(char*);
void addItem(ListItem*,char*);

