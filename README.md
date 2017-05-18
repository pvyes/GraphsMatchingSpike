# GraphsMatchingSpike
A trial application to find subgraphs with jgrapht

plan:

1. Set up a Maven project ok
1a. Import Edge and Node classes ok (Logging disabled)
1b. Import DP and SuC ok (Conditions disabled)

2. Create a DesignPattern in graph-style with jgrapht, using node and edge classes (copy and paste) ok
2a. A simple DP with only relationships and their types
2b. A DP with relations, multiplicities and a abstract class
2c. A DP like 2b with attributes and attribute-types

3. Create a SystemUnderConsideration in graph-style with jgrapht, using node and edge classes (copy and paste)
3a. A SuC matching 2a totally, with names and relations
3b. A SuC matching 2a but with other names of nodes and edges
3c. A Suc matching 2a but with other relationtype
3d. A SuC not matching 2a in structure of relations

4. Create testers for these classes
5. Make a comparator for the nodes, edges ...
6. Make a matcher using a findSubGrapg algorithm and using the Comparators of 5.
7. Test the matcher with 2a and 3a
8. Rework the Comparator using Conditions
9. Test the matcher again with 2a and 3a
10. Gradually increase complexity