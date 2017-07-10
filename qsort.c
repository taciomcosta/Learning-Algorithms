#include <stdio.h>
#include <stdlib.h>

int a[5] = {5, 4, 1, 3, 2};


int compare(const void *n1, const void *n2)
{
        /* cast void pointers to integer pointers */
        int a = *(int *) n1;
        int b = *(int *) n2;

        return a - b;
}

int main(void)
{

        /* sort an array of integer */
        qsort(a, 5, sizeof(int), compare);
        for (int i = 0; i < 5; i++) {
                printf("%i ", a[i]);
        }
        putchar('\n');
}
