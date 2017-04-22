/* Reads an undefined number of words and sort them
   Author: Tácio M. Costa
   Credits: BLUEPIX, David C. Rankin, Cerulean Chelonii
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define WORD_SIZE 11

int cmpstrings(const void *a, const void *b)
{
        return strcmp(*(char * const *)a, *(char * const *)b);
}

int main(void)
{
        char word[WORD_SIZE],
                **set = NULL;
        int j,
                numberWord = 0;

        for (;;) {
                /* INPUT */
                printf("Insert a word, or '-' to stop: ");
                fgets(word, WORD_SIZE, stdin);

                /* remove trailing '\n' */
                size_t len = strlen (word);
                if (word[len - 1] == '\n')
                        word[--len] = 0;

                /* exit condition */
                if (len == 1 && *word == '-')
                        break;

                /* PROCESS - validate all allocations */
                void *tmp = realloc (set, sizeof *set * (numberWord + 1));
                if (!tmp) {
                        fprintf (stderr, "error: virtual memory exhausted - realloc.\n");
                        break;
                }
                set = tmp;

                set[numberWord] = malloc(sizeof(char) * len+1);
                strcpy(set[numberWord], word);
                numberWord++;

        }

        /* sorting */
        qsort(set, numberWord, sizeof(* set), cmpstrings);

        /* output */
        printf("\nSORTED:\n");
        for (j = 0; j <  numberWord; j++) {
                printf("%s\n", set[j]);
                free(set[j]);   /* free memory for each word */
        }

        free(set); /* free "array of pointers" */
        putchar('\n');

        return 0;
}
