%{
#include "y.tab.h"
extern int btotal,atotal;
%}


%%

"A"	{atotal++; return ACHAR;}
"B"	{btotal++; return BCHAR;}
.	;
