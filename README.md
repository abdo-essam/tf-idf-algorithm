# tf-idf-algorithm
Implementation of the tf-idf algorithm to calculate the similarity between documents using cosine similarity.

The TfIdf class contains methods to calculate term frequency (tf) and inverse document frequency (idf). The tfCalculator method takes an array of all the words in a document and a term to check as input and returns the term frequency of the term in the document. The idfCalculator method takes a list of arrays, each containing all the words in a document and a term to check as input and returns the inverse document frequency score of the term across all the documents.

The CosineSimilarity class has a method cosineSimilarity to calculate the cosine similarity between two document vectors. It takes two document vectors as input, calculates the dot product, magnitude of each vector, and returns the cosine similarity score.

The DocumentParser class has methods to read files, tokenize the documents into terms, and create a term frequency-inverse document frequency (tf-idf) vector for each document. The parseFiles method reads all the files in a given folder and stores the terms of each document in an array. The tfIdfCalculator method uses the TfIdf class to calculate the tf-idf score for each term in each document and stores the document vectors in a list. The getCosineSimilarity method calculates the cosine similarity between all pairs of documents and prints the results.

Overall, this code can be used to calculate the similarity between a set of documents based on the words they contain. It is commonly used in information retrieval and text mining tasks.
