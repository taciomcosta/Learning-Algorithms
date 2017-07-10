#include <stdio.h>
#include <string.h>
#include <sqlite3.h>

int main(void)
{

        sqlite3 *db;
        char *zErrMsg = 0;
        int rc;
        char username[30];
        char password[30];
        char query[100] = "INSERT INTO users VALUES (\"";

        /* connect to database */
        rc = sqlite3_open("test.db", &db);

        /* check if it was successfully connected */
        if (rc) {

                fprintf(stderr,
                        "can't open the database: %s\n",
                        sqlite3_errmsg(db));

        } else {

                fprintf(stderr, "Opened database successfully!\n");

        }

        /* get user information */
        printf("Username: ");
        scanf("%s", username);

        printf("Password: ");
        scanf("%s", password);

        /* set full query */
        strcat(query, username);
        strcat(query, "\", \"");
        strcat(query, password);
        strcat(query, "\");");
        puts(query);

        /* sign user up */
        rc = sqlite3_exec(db, query, NULL, 0, &zErrMsg);

        /* close connection */
        sqlite3_close(db);

        return 0;
}
