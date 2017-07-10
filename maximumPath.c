#include <stdio.h>

#define M 15

/* finds maximum total of a matrix */
int max(int m[][M], int i, int j)
{
        int maxLeft = 0;
        int maxRight = 0;

        /* if it has branches */
        if (i + 1 < M) {

                /* find left branch maximum total */
                maxLeft = max(m, i + 1, j);

                /* find right branch maximum total */
                maxRight = max(m, i + 1, j + 1);

                /* returns the greatest branch max + the current element */
                if (maxLeft >= maxRight)
                        return maxLeft + m[i][j];
                else
                        return maxRight + m[i][j];


        /* if it has no branches, then it's the maximum total*/
        } else {

                return m[i][j];
        }
}

int main(void)
{
        /* load triangle as matrix */
        int m[M][M] = {{75, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {95, 64, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {17, 47, 82, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {18, 35, 87, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {20, 04, 82, 47, 65, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {19, 01, 23, 75, 03, 34, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                       {88, 02, 77, 73, 07, 63, 67, 0, 0, 0, 0, 0, 0, 0, 0},
                       {99, 65, 04, 28, 06, 16, 70, 92, 0, 0, 0, 0, 0, 0, 0},
                       {41, 41, 26, 56, 83, 40, 80, 70, 33, 0, 0, 0, 0, 0, 0},
                       {41, 48, 72, 33, 47, 32, 37, 16, 94, 29, 0, 0, 0, 0, 0},
                       {53, 71, 44, 65, 25, 43, 91, 52, 97, 51, 14, 0, 0, 0, 0},
                       {70, 11, 33, 28, 77, 73, 17, 78, 39, 68, 17, 57, 0, 0, 0},
                       {91, 71, 52, 38, 17, 14, 91, 43, 58, 50, 27, 29, 48, 0, 0},
                       {63, 66, 04, 68, 89, 53, 67, 30, 73, 16, 69, 87, 40, 31, 0},
                       {4, 62, 98, 27, 23, 9, 70, 98, 73, 93, 38, 53, 60, 04, 23}};

        printf("maximum total: %i\n", max(m, 0, 0));

        return 0;
}
