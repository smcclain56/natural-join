# Relational Database Natural Join Algorithms
This project was an assignment given in my undergraduate class, "Principles of Database Systems". We were given a problem in which we implemented several natural join operations in relational database systems, including nested-loop join, hash join, and sort-merge join. The program starts by reading in each file from the data directory and processing the information into appropiate data structures. After all the files from the data directory have been input, the program should prompt the user to enter the relations across which they'd like to perform the natural join and which type of natural join they would like to perform. When the join is done, the program will output the resulting relation, the time elapsed in milliseconds, and the number of rows returned in the result set. The program will allow the user to continue performing joins until they choose option 2 from the menu, which quits the program.

# Features
If the user enters any relations that do not exist (case insensitive) the program throws an IllegalArgumentException and exits. Otherwise, it presents the user with a menu, allowing the user to select from one of the three join algorithms:

- Natural-Loop Join - The nested-loop join is the naive approach. It loops through each tuple in the relations R and S and produces a concatenated tuple if the values of their common attributes are equal. 
- Hash Join - The hash join algorithm has two distinct phases. Phase I builds a hashmap over relation R. Each map entry is keyed on the value of the common attribute and stores the tuple in R. It is important to note that for this reason that common attribute R.c must have unique values (e.g., be a key) in R. If R.c were not unique, then different tuples would collide and overwite each other in the hashamp. Phase II then iterates through each tuple in S and searches for the value of its common attribute in the hash map. If it exists in the map, then there is equivalence on common attributes in both relations, and the concatenated tuple is retained in the result. Otherwise the tuple in S is discarded. 
- Sort-Merge Join - The sort-merge join assumes that the tuples in both relations are sorted on their common attributes. We use cursors i and j to track the row positions of relations R and S, respectively. We shift the cursors down until we exceed one of the relations. When a match on their common attributes is found, we enter the merge phase, in which we concatenate both tuples, while moving S's cursor down. The merge phase is a nested loop on the tuples that match. When S's cursors refers to a tuple that is no longer a match, then R's cursor moves increments and S's cursor resets

When the user is done making their choice, the join processing should then occur, and the program will output the resulting relation, the time elapsed in milliseconds, and the number of rows returned in the result set. If a common attribute does not exist between the tables, the program will throw an exception. The program will allow the user to continue performing joins until they choose option 2 from the menu, which quits the program.

# Example
<pre>
========================= Welcome to Join Engine ==========================
Available relations:
[customers, employees, offices, orderdetails, orders, payments, productlines, products]

[Option 1] Complete a join operation between two relations
[Option 2] Exit
1
Pick your first relation: 
offices
Pick your second relation: 
employees
Your selection (separated by space): offices employees
1. Nested Loop Join
2. Hash Join
3. Sort-Merge Join
1
Your selection: 1
officeCode	city	phone	addressLine1	addressLine2	state	country	postalCode	territory	employeeNumber	lastName	firstName	extension	email	reportsTo	jobTitle	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1002	Murphy	Diane	x5800	dmurphy@classicmodelcars.com	NULL	President	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1056	Patterson	Mary	x4611	mpatterso@classicmodelcars.com	1002	VP Sales	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1076	Firrelli	Jeff	x9273	jfirrelli@classicmodelcars.com	1002	VP Marketing	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1143	Bow	Anthony	x5428	abow@classicmodelcars.com	1056	Sales Manager (NA)	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1165	Jennings	Leslie	x3291	ljennings@classicmodelcars.com	1143	Sales Rep	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1166	Thompson	Leslie	x4065	lthompson@classicmodelcars.com	1143	Sales Rep	
2	Boston	+1 215 837 0825	1550 Court Place	Suite 102	MA	USA	02107	NA	1188	Firrelli	Julie	x2173	jfirrelli@classicmodelcars.com	1143	Sales Rep	
2	Boston	+1 215 837 0825	1550 Court Place	Suite 102	MA	USA	02107	NA	1216	Patterson	Steve	x4334	spatterson@classicmodelcars.com	1143	Sales Rep	
3	NYC	+1 212 555 3000	523 East 53rd Street	apt. 5A	NY	USA	10022	NA	1286	Tseng	Foon Yue	x2248	ftseng@classicmodelcars.com	1143	Sales Rep	
3	NYC	+1 212 555 3000	523 East 53rd Street	apt. 5A	NY	USA	10022	NA	1323	Vanauf	George	x4102	gvanauf@classicmodelcars.com	1143	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1102	Bondur	Gerard	x5408	gbondur@classicmodelcars.com	1056	Sale Manager (EMEA)	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1337	Bondur	Loui	x6493	lbondur@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1370	Hernandez	Gerard	x2028	ghernande@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1401	Castillo	Pamela	x2759	pcastillo@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1702	Gerard	Martin	x2312	mgerard@classicmodelcars.com	1102	Sales Rep	
5	Tokyo	+81 33 224 5000	4-1 Kioicho	NULL	Chiyoda-Ku	Japan	102-8578	Japan	1621	Nishi	Mami	x101	mnishi@classicmodelcars.com	1056	Sales Rep	
5	Tokyo	+81 33 224 5000	4-1 Kioicho	NULL	Chiyoda-Ku	Japan	102-8578	Japan	1625	Kato	Yoshimi	x102	ykato@classicmodelcars.com	1621	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1088	Patterson	William	x4871	wpatterson@classicmodelcars.com	1056	Sales Manager (APAC)	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1611	Fixter	Andy	x101	afixter@classicmodelcars.com	1088	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1612	Marsh	Peter	x102	pmarsh@classicmodelcars.com	1088	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1619	King	Tom	x103	tking@classicmodelcars.com	1088	Sales Rep	
7	London	+44 20 7877 2041	25 Old Broad Street	Level 7	NULL	UK	EC2N 1HN	EMEA	1501	Bott	Larry	x2311	lbott@classicmodelcars.com	1102	Sales Rep	
7	London	+44 20 7877 2041	25 Old Broad Street	Level 7	NULL	UK	EC2N 1HN	EMEA	1504	Jones	Barry	x102	bjones@classicmodelcars.com	1102	Sales Rep	

Time = 0.9976ms
========================= Welcome to Join Engine ==========================
Available relations:
[customers, employees, offices, orderdetails, orders, payments, productlines, products]

[Option 1] Complete a join operation between two relations
[Option 2] Exit
1
Pick your first relation: 
offices
Pick your second relation: 
employees
Your selection (separated by space): offices employees
1. Nested Loop Join
2. Hash Join
3. Sort-Merge Join
2
Your selection: 2
officeCode	city	phone	addressLine1	addressLine2	state	country	postalCode	territory	employeeNumber	lastName	firstName	extension	email	reportsTo	jobTitle	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1002	Murphy	Diane	x5800	dmurphy@classicmodelcars.com	NULL	President	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1056	Patterson	Mary	x4611	mpatterso@classicmodelcars.com	1002	VP Sales	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1076	Firrelli	Jeff	x9273	jfirrelli@classicmodelcars.com	1002	VP Marketing	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1088	Patterson	William	x4871	wpatterson@classicmodelcars.com	1056	Sales Manager (APAC)	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1102	Bondur	Gerard	x5408	gbondur@classicmodelcars.com	1056	Sale Manager (EMEA)	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1143	Bow	Anthony	x5428	abow@classicmodelcars.com	1056	Sales Manager (NA)	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1165	Jennings	Leslie	x3291	ljennings@classicmodelcars.com	1143	Sales Rep	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1166	Thompson	Leslie	x4065	lthompson@classicmodelcars.com	1143	Sales Rep	
2	Boston	+1 215 837 0825	1550 Court Place	Suite 102	MA	USA	02107	NA	1188	Firrelli	Julie	x2173	jfirrelli@classicmodelcars.com	1143	Sales Rep	
2	Boston	+1 215 837 0825	1550 Court Place	Suite 102	MA	USA	02107	NA	1216	Patterson	Steve	x4334	spatterson@classicmodelcars.com	1143	Sales Rep	
3	NYC	+1 212 555 3000	523 East 53rd Street	apt. 5A	NY	USA	10022	NA	1286	Tseng	Foon Yue	x2248	ftseng@classicmodelcars.com	1143	Sales Rep	
3	NYC	+1 212 555 3000	523 East 53rd Street	apt. 5A	NY	USA	10022	NA	1323	Vanauf	George	x4102	gvanauf@classicmodelcars.com	1143	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1337	Bondur	Loui	x6493	lbondur@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1370	Hernandez	Gerard	x2028	ghernande@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1401	Castillo	Pamela	x2759	pcastillo@classicmodelcars.com	1102	Sales Rep	
7	London	+44 20 7877 2041	25 Old Broad Street	Level 7	NULL	UK	EC2N 1HN	EMEA	1501	Bott	Larry	x2311	lbott@classicmodelcars.com	1102	Sales Rep	
7	London	+44 20 7877 2041	25 Old Broad Street	Level 7	NULL	UK	EC2N 1HN	EMEA	1504	Jones	Barry	x102	bjones@classicmodelcars.com	1102	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1611	Fixter	Andy	x101	afixter@classicmodelcars.com	1088	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1612	Marsh	Peter	x102	pmarsh@classicmodelcars.com	1088	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1619	King	Tom	x103	tking@classicmodelcars.com	1088	Sales Rep	
5	Tokyo	+81 33 224 5000	4-1 Kioicho	NULL	Chiyoda-Ku	Japan	102-8578	Japan	1621	Nishi	Mami	x101	mnishi@classicmodelcars.com	1056	Sales Rep	
5	Tokyo	+81 33 224 5000	4-1 Kioicho	NULL	Chiyoda-Ku	Japan	102-8578	Japan	1625	Kato	Yoshimi	x102	ykato@classicmodelcars.com	1621	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1702	Gerard	Martin	x2312	mgerard@classicmodelcars.com	1102	Sales Rep	

Time = 0.2741ms
========================= Welcome to Join Engine ==========================
Available relations:
[customers, employees, offices, orderdetails, orders, payments, productlines, products]

[Option 1] Complete a join operation between two relations
[Option 2] Exit
1
Pick your first relation: 
offices
Pick your second relation: 
employees
Your selection (separated by space): offices employees
1. Nested Loop Join
2. Hash Join
3. Sort-Merge Join
3
Your selection: 3
officeCode	city	phone	addressLine1	addressLine2	state	country	postalCode	territory	employeeNumber	lastName	firstName	extension	email	reportsTo	jobTitle	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1002	Murphy	Diane	x5800	dmurphy@classicmodelcars.com	NULL	President	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1056	Patterson	Mary	x4611	mpatterso@classicmodelcars.com	1002	VP Sales	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1076	Firrelli	Jeff	x9273	jfirrelli@classicmodelcars.com	1002	VP Marketing	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1143	Bow	Anthony	x5428	abow@classicmodelcars.com	1056	Sales Manager (NA)	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1165	Jennings	Leslie	x3291	ljennings@classicmodelcars.com	1143	Sales Rep	
1	San Francisco	+1 650 219 4782	100 Market Street	Suite 300	CA	USA	94080	NA	1166	Thompson	Leslie	x4065	lthompson@classicmodelcars.com	1143	Sales Rep	
2	Boston	+1 215 837 0825	1550 Court Place	Suite 102	MA	USA	02107	NA	1188	Firrelli	Julie	x2173	jfirrelli@classicmodelcars.com	1143	Sales Rep	
2	Boston	+1 215 837 0825	1550 Court Place	Suite 102	MA	USA	02107	NA	1216	Patterson	Steve	x4334	spatterson@classicmodelcars.com	1143	Sales Rep	
3	NYC	+1 212 555 3000	523 East 53rd Street	apt. 5A	NY	USA	10022	NA	1286	Tseng	Foon Yue	x2248	ftseng@classicmodelcars.com	1143	Sales Rep	
3	NYC	+1 212 555 3000	523 East 53rd Street	apt. 5A	NY	USA	10022	NA	1323	Vanauf	George	x4102	gvanauf@classicmodelcars.com	1143	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1102	Bondur	Gerard	x5408	gbondur@classicmodelcars.com	1056	Sale Manager (EMEA)	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1337	Bondur	Loui	x6493	lbondur@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1370	Hernandez	Gerard	x2028	ghernande@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1401	Castillo	Pamela	x2759	pcastillo@classicmodelcars.com	1102	Sales Rep	
4	Paris	+33 14 723 4404	43 Rue Jouffroy Dabbans	NULL	NULL	France	75017	EMEA	1702	Gerard	Martin	x2312	mgerard@classicmodelcars.com	1102	Sales Rep	
5	Tokyo	+81 33 224 5000	4-1 Kioicho	NULL	Chiyoda-Ku	Japan	102-8578	Japan	1621	Nishi	Mami	x101	mnishi@classicmodelcars.com	1056	Sales Rep	
5	Tokyo	+81 33 224 5000	4-1 Kioicho	NULL	Chiyoda-Ku	Japan	102-8578	Japan	1625	Kato	Yoshimi	x102	ykato@classicmodelcars.com	1621	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1088	Patterson	William	x4871	wpatterson@classicmodelcars.com	1056	Sales Manager (APAC)	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1611	Fixter	Andy	x101	afixter@classicmodelcars.com	1088	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1612	Marsh	Peter	x102	pmarsh@classicmodelcars.com	1088	Sales Rep	
6	Sydney	+61 2 9264 2451	5-11 Wentworth Avenue	Floor #2	NULL	Australia	NSW 2010	APAC	1619	King	Tom	x103	tking@classicmodelcars.com	1088	Sales Rep	
7	London	+44 20 7877 2041	25 Old Broad Street	Level 7	NULL	UK	EC2N 1HN	EMEA	1501	Bott	Larry	x2311	lbott@classicmodelcars.com	1102	Sales Rep	
7	London	+44 20 7877 2041	25 Old Broad Street	Level 7	NULL	UK	EC2N 1HN	EMEA	1504	Jones	Barry	x102	bjones@classicmodelcars.com	1102	Sales Rep	

Time = 1.2184ms
========================= Welcome to Join Engine ==========================
Available relations:
[customers, employees, offices, orderdetails, orders, payments, productlines, products]

[Option 1] Complete a join operation between two relations
[Option 2] Exit
2
Exiting... bye.
</pre>
# Credits
My professor, David Chiu, supplied the data directory as the starter files for this assignment.
