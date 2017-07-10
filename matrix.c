#include <stdio.h>
#include <string.h>
#include <stdio_ext.h> // used for __fpurge() function

#define N_MEMBERS 3

struct name {
        char firstName[10];
        char lastName[10];
};

struct person {
        struct name name;
        int age;
        char gender;
};

main()
{
        /* vars */
        struct person family[N_MEMBERS];
        int i;

        /* input */
        for (i = 0; i < N_MEMBERS; i++) {
                printf("\nMember %i\n", i + 1);

                printf("First name: ");
                /* cleans keyboard buffer */
                __fpurge(stdin);
                gets(family[i].name.firstName);

                printf("Last name: ");
                __fpurge(stdin);
                gets(family[i].name.lastName);

                printf("Age: ");
                scanf("%i", &family[i].age);

                getchar();
                printf("Gender(M/F): ");
                scanf("%c", &family[i].gender);
        }

        /* output */
        printf("\n\n === FAMILY MEMBERS === \n\n");
        for (i = 0; i < N_MEMBERS; i++) {
                printf("\n%s %s\n", family[i].name.firstName, family[i].name.lastName);
                printf("%i %c\n", family[i].age, family[i].gender);
        }
}
