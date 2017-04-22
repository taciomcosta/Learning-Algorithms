/* recover.c finds 50 lost JPEG images that are into a MS CARD */
#include <stdio.h>
#include <stdlib.h>

/* Prototypes */
void createImg(int);
int isJpeg();

/* Global vars */
char imgName[7];
FILE *img;
int *signature;
int firstByte;
int cont = 0;

int main(int argc, char *argv[])
{
    /* Check the number of arguments */
    if (argc != 2) {
        fprintf(stderr, "Usage: ./recover file.raw\n");
        return 1;
    }

    /* Try to open the .raw file */
    FILE *raw = fopen(argv[1], "r");
    if (raw == NULL) {
        fprintf(stderr, "Cannot open the input file\n");
        return 2;
    }

    /* Jump the 512 first bytes */
    fseek(raw, 512, SEEK_CUR);

    /* allocates 512B in memory */
    signature = malloc(512);/* read 1B */

    /* while the file is possible to be read */
    while (fread(signature, 1, 512, raw) != 0) {
        /* If the 512B starts with a JPEG signature */
        if (isJpeg()) {
            /* if there is a file to be closed */
            if (cont > 0)
                fclose(img);
            /* create a new file */
            createImg(cont);
            cont++;
        }
        /* write 512B , if it's not a initial trash block*/
        if (cont > 0)
            fwrite(signature, 1, 512, img);
    }

    /* close last file */
    fclose(img);

    /* close .raw file */
    fclose(raw);

    /* free allocated space */
    free(signature);

    /* Return 0, if succeeded */
    return 0;
}

/* create img name and file */
void createImg(int i)
{
    /* Creates img name */
    sprintf(imgName, "%03i.jpg", i);

    /* Creates img file */
    img = fopen(imgName, "w");
}

/* checks if the 512B starts with JPEG signature */
int isJpeg()
{
    int antiByte = 0xf0ffffff;

    int res = 0xe0ffd8ff;

    if ((signature[0] & antiByte) == res)
        return 1;
    else
        return 0;
}
