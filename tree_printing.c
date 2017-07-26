/*
EX:
    4
  2   6
 1 3 5 7
*/

#include <stdio.h>
#include <stdlib.h>

typedef struct tree {
        int n;
        struct tree *left;
        struct tree *right;
} tree;

int get_tree_depth(tree *t)
{
        /* if has no tree created */
        if (t == NULL)
                return 0;
        int leftDepth = 1;
        int rightDepth = 1;
        /* backup head */
        tree *head = t;
        /* check left-side depth */
        while (t->left) {
                leftDepth++;
                t = t->left;
        }
        /* reset pointer to head */
        t = head;
        /* check right-side depth */
        while (t->right) {
                rightDepth++;
                t = t->right;
        }
        return leftDepth >= rightDepth ? leftDepth : rightDepth;
}

void print_line(tree *t, int level)
{
        /* if it's the level meant to be printed */
        if (level == 0) {
                printf("%i ", t->n);
                return;
        }
        /* call print_line to each side */
        if (t->left != NULL)
                print_line(t->left, level - 1);
        if (t->right != NULL)
                print_line(t->right, level - 1);
}

void print_tree(tree *t)
{
        printf("\n\n======== TREE =======\n");
        /* get tree depth */
        int depth = get_tree_depth(t);
        /* print each level of tree according to depth */
        for (int i = 0; i < depth; i++) {
                /* print spaces */
                for (int j = i; j < depth - 1; j++) {
                        putchar(' ');
                }
                print_line(t, i);
                putchar('\n');
        }
}

void init_node(tree *node, int n)
{
        node->n = n;
        node->left = NULL;
        node->right = NULL;
}

tree *create_tree(int n)
{
        /* allocate memory for tree */
        tree *t = malloc(sizeof(tree));
        init_node(t, n);
        return t;
}

void create_node(tree *t, int n)
{
        /* create node */
        tree *node = create_tree(n);
        /* travel to correct position */
        while (1) {
                /* left */
                if (node->n <= t->n) {
                        if (t->left == NULL) {
                                t->left = node;
                                break;
                        } else {
                                t = t->left;
                        }
                /* right */
                } else {
                        if (t->right == NULL) {
                                t->right = node;
                                break;
                        } else {
                                t = t->right;
                        }
                }
        }
}

int main(void)
{
        int n;
        tree *head;
        /* get 1st input */
        printf("n: ");
        scanf("%i", &n);
        /* if it's not valid */
        if (n < 0)
                return 0;
        /* create tree */
        head = create_tree(n);
        do {
                /* get remaining inputs */
                printf("n: ");
                scanf("%i", &n);
                if (n < 0)
                        break;
                /* create node */
                create_node(head, n);
        } while(1);
        print_tree(head);
        return 0;
}
