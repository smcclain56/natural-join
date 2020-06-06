# Relational Database Natural Join Algorithms
This project was an assignment given in my undergraduate class, "Principles of Database Systems". We were given a problem in which we implemented several natural join operations in relational database systems, including nested-loop join, hash join, and sort-merge join. After all the files from the data directory have been input, the program should prompt the user to enter the relations across which they'd like to perform the natrual join. 

# Features
If the user enters any relations that do not exist (case insensitive) the program throws an IllegalArgumentException and exits. Otherwise, it presents the user with a menu, allowing the user to select from one of the three join algorithms:

- Natural-Loop Join - The nested-loop join is the naive approach. It loops through each tuple in the relations R and S and produces a concatenated tuple if the values of their common attributes are equal. 
- Hash Join - The hash join algorithm has two distinct phases. Phase I builds a hashmap over relation R. Each map entry is keyed on the value of the common attribute and stores the tuple in R. It is important to note that for this reason that common attribute R.c must have unique values (e.g., be a key) in R. If R.c were not unique, then different tuples would collide and overwite each other in the hashamp. Phase II then iterates through each tuple in S and searches for the value of its common attribute in the hash map. If it exists in the map, then there is equivalence on common attributes in both relations, and the concatenated tuple is retained in the result. Otherwise the tuple in S is discarded. 
- Sort-Merge Join - The sort-merge join assumes that the tuples in both relations are sorted on their common attributes. We use cursors i and j to track the row positions of relations R and S, respectively. We shift the cursors down until we exceed one of the relations. When a match on their common attributes is found, we enter the merge phase, in which we concatenate both tuples, while moving S's cursor down. The merge phase is a nested loop on the tuples that match. When S's cursors refers to a tuple that is no longer a match, then R's cursor moves increments and S's cursor resets

When the user is done making their choice, the join processing should then occur, and the program will output the resulting relation, the time elapsed in milliseconds, and the number of rows returned in the result set. If a common attribute does not exist between the tables, the program will throw an exception. 

# Credits
My professor, David Chiu, supplied the data directory as the starter files for this assignment.
