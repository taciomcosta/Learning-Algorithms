/* DESCRIPTION: simple calculator using an array of function pointers */
#include <stdio.h>

int add(int a, int b)
{
        return a + b;
}

int sub(int a, int b)
{
        return a - b;
}

int mul(int a, int b)
{
        return a * b;
}

int div(int a, int b)
{
        return a / b;
}


int main(void)
{
        /* array of function pointers */
        int (*calculate[]) (int, int) = {add, sub, mul, div};
        int option, n1, n2;

        /* get user option */
        printf("1 - Add\n");
        printf("2 - Subtract\n");
        printf("3 - Multiply\n");
        printf("4 - Divide\n");
        printf("Option: ");
        scanf("%i", &option);

        /* get n1 */
        printf("n1: ");
        scanf("%i", &n1);

        /* get n1 */
        printf("n2: ");
        scanf("%i", &n2);

        /* print result according to option selected */
        printf("Result = %i\n",
               (calculate[option - 1])(n1, n2) /* call function option - 1 from the array of function pointers */
                );

        return 0;
}
