/*
 * Author: Fabian Bergmark
*/

#include <tuple>
#include <vector>

namespace popup {
    namespace unionfind {
        template<typename T>
        class Tree;

        template<typename T>
        class UnionFind {
        public:
            /*
             * @param n: The number of elements. Initially every element
             * 0..n will be in disjoint sets {0}..{n}.
             */
            UnionFind(const long n) {
                trees = new Tree<T>[n];
            }

            virtual ~UnionFind() {
                delete[] trees;
            }

            /*
             * Unions the set containing a and b.
             * @param a: A representant from the first set.
             * @param b: A representant from the second set.
             */
            void createUnion(const T& a, const T& b) {
                Tree<T>* aTree = trees[a].find();
                Tree<T>* bTree = trees[b].find();

                if (aTree == bTree)
                    return;

                if (aTree->rank < bTree->rank)
                    aTree->parent = bTree;
                else if (aTree->rank > bTree->rank)
                    bTree->parent = aTree;
                else {
                    bTree->parent = aTree;
                    ++aTree->rank;
                }
            }

            /*
             * @param a: A representant from the first set.
             * @param b: A representant from the second set.
             * @return: True if the parameters represents the same set,
             * false otherwise.
             */
            bool same(const T& a, const T& b) {
                Tree<T>* aTree = trees[a].find();
                Tree<T>* bTree = trees[b].find();
                return aTree == bTree;
            }

            const Tree<T>* getTrees() const {
                return trees;
            }

        private:
            Tree<T>* trees;
        };

        template<typename T>
        class Tree {
        public:
            Tree() : rank(0) {
                parent = this;
            }

            Tree* find() {
                if (parent != this)
                    parent = parent->find();
                return parent;
            }

            const Tree* find() const {
                if (parent != this)
                    parent = parent->find();
                return parent;
            }

            mutable long rank;
            mutable Tree* parent;
        };
    }
}
