#include <stdio.h>
#include <stdlib.h>             /* for qsort() */
#include <string.h>

/* vars */
int NUM_ADS = 7;
char *ADS[] = {
        "William: SBM GSOH likes sports, TV, dining",
        "Matt: SWM NS likes art, movies, theater",
        "Luis: SLM ND likes books, theater, art",
        "Mike: DWM DS likes trucks, sports and bieber",
        "Peter: SAM likes chess, working out and art",
        "Josh: SJM likes sports, movies and theater",
        "Jed: DBM likes theater, books and dining"
};
char *results[7] = {NULL};/* initialize result with NULL */;

int cmp_strings(const void* s1, const void* s2)
{
        /* cast pointers */
        char *r1 = (char *) s1;
        char *r2 = (char *) s2;

        /* compare them */
        return strcmp(r1, r2);
}

int sports_no_bieber(char *s)
{
        return strstr(s, "sports") && !strstr(s, "bieber");
}

int bieber(char *s)
{
        return strstr(s, "bieber") ? 1 : 0;
}

void find(int (*match) (char*))
{
        puts("--- RESULTS ---");
        for (int i = 0; i < NUM_ADS; i++)
                if (match(ADS[i]))
                        results[i] = ADS[i];
}

int main(void)
{

        /* match results of an array of strings,
           according to a passed condition */
        find(sports_no_bieber);
        qsort(results, 7, sizeof(char *), cmp_strings);
        for (int i = 0; i < 7; i++)
                if (results[i] != NULL)
                        puts(results[i]);
}
