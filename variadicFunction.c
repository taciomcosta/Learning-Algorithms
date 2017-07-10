#include <stdio.h>
#include <stdarg.h>             /* used for variable length of args in functions */

void print_words(int length, ...)
{
        /* create a list of args*/
        va_list valist;

        /* initialize list */
        va_start(valist, length); /* macro to start valist */

        /* print a returned arg */
        for (int i = 0; i < length; i++)
                printf("%s\n", va_arg(valist, char[])); /* macro for accessing args */

        /* clean memory reserved */
        va_end(valist);         /* macro to end list */
}

int main(void)
{
        print_words(5, "Hello", "world", "world", "sweet", "world");
}
