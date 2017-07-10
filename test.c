#include <stdio.h>
#include <stdlib.h>

typedef struct node {
        int n;
        struct node *left;
        struct node *right;
} node;

node *create_node(int n)
{
        /* create node */
        node *ptr = malloc(sizeof(node));

        /* initialize */
        ptr->n = n;
        ptr->left = NULL;
        ptr->right = NULL;

        return ptr;
}

int get_tree_depth(node *tree)
{

        int left_depth = 0;
        int right_depth = 0;

        /* get depth of the left */
        if (tree->left != NULL)
                left_depth = get_tree_depth(tree->left);

        /* get depth of the right */
        if (tree->right != NULL)
                right_depth = get_tree_depth(tree->right);

        /* return the greatest depth */
        if (left_depth >= right_depth)
                return left_depth + 1;

        return right_depth + 1;
}

void print_level(node *tree, int level)
{
        if (level == 0) {
                printf("%i ", tree->n);
                return;
        }

        if (tree->left != NULL)
                print_level(tree->left, level - 1);
        else
                printf(" ");

        if (tree->right != NULL)
                print_level(tree->right, level - 1);
        else
                printf(" ");
}

void print_tree(node *tree)
{
        /* get tree depth */
        int treeDepth = get_tree_depth(tree);

        /* for each level of the tree */
        for (int i = 0; i < treeDepth; i++) {

                /* print spaces */
                for (int j = i + 1; j < treeDepth; j++)
                        putchar(' ');

                /* print a unique level */
                print_level(tree, i);

                putchar('\n');
        }
}

int main(void)
{
        /* vars */
        node *tree;

        /* create tree */
        tree = create_node(3);

        tree->left = create_node(2);
        tree->right = create_node(5);

        tree->left->left = create_node(1);
        tree->left->right = create_node(6);

        tree->right->right = create_node(7);
        tree->right->right->left = create_node(8);
        tree->right->right->right = create_node(10);


        print_tree(tree);

        return 0;
}
