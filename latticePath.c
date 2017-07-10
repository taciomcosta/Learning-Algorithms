/* Lattice Path - Problem 15 from Project Euler */
/* It's an inefficient algorithm, but it works! */
#include <stdio.h>

#define N 20
unsigned long latticePlace(int row, int col)
{
        if (row == N || col == N)
                return 1;

        unsigned long total = 0;

        if (col < N)
                total += latticePlace(row, col + 1);
        if (row < N)
                total += latticePlace(row + 1, col);

        return total;
}

int main(void)
{
        printf("%lu\n", latticePlace(0, 0));
        return 0;
}
