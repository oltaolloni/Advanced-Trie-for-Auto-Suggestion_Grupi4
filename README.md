# Auto-Suggest Trie with Error Tolerance

## **Overview**
This project implements an **Advanced Trie for Auto-Suggestion with Error Tolerance**. The application provides word suggestions based on a user query and allows up to three character errors (substitutions, deletions, or insertions). The underlying data structure, a Trie, efficiently stores and retrieves words to handle queries interactively and support approximate matches.

---

## **Features**
- **Trie-Based Word Storage**: Efficiently stores dictionary words for fast lookups.
- **Error-Tolerant Search**: Suggests words even with up to three character mismatches in the query.
- **Interactive CLI Interface**: Allows users to input queries and get real-time suggestions.
- **Customizable Error Threshold**: Supports adjusting the maximum allowable errors.
- **File-Based Dictionary Loading**: Reads words from a text file to populate the Trie.

---

## **Data Structure Explanation**

### **Trie (Prefix Tree)**
A Trie is a tree-like data structure used for storing strings efficiently. Each node represents a character, and paths in the tree represent words or prefixes. This structure is especially useful for tasks like auto-suggestion and prefix matching.

#### **Structure**:
1. **Nodes**:
   - Represent characters.
   - Use a `Map` to store child nodes for each possible next character.
   - Use a boolean flag `isEndOfWord` to indicate the end of a valid word.
2. **Root**:
   - The root is an empty node that serves as the entry point for all words in the Trie.

#### **Key Operations**:
- **Insert**:
  Adds words character by character to the Trie, creating new nodes as needed.
- **Search**:
  Traverses the Trie to check if a word exists.
- **DFS for Suggestions**:
  Recursively explores all possible words that match a query, considering errors like substitutions, insertions, and deletions.

---

## **How It Works**

### **1. Building the Trie**
- The program reads a dictionary file (`dictionary.txt`) line by line.
- Each word is inserted into the Trie using the `insert` method.
- This method iterates over each character of the word, creating nodes in the Trie for each unique character.

### **2. Query Handling**
- The user inputs a query interactively.
- The `findSuggestions` method retrieves words that match the query within a specified error tolerance (default: 3).
- **Depth-First Search (DFS)**:
  - Explores all possible paths in the Trie while tracking:
    - Exact character matches.
    - Character substitutions.
    - Character insertions.
    - Character deletions.

### **3. Error Tolerance**
- The DFS function uses `editsRemaining` to track the number of allowable mismatches.
- Suggestions are collected whenever a valid word (`isEndOfWord`) is reached within the error threshold.

---

## **Code Explanation**

### **Classes**
#### 1. `TrieNode`
- Represents each node in the Trie.
- **Fields**:
  - `children`: A map storing child nodes (character to node mapping).
  - `isEndOfWord`: A boolean indicating whether the node completes a valid word.

#### 2. `Trie`
- Manages the overall Trie structure and provides methods for insertion and querying.
- **Methods**:
  - `insert(String word)`:
    Inserts a word into the Trie character by character.
  - `findSuggestions(String query, int maxEdits)`:
    Finds suggestions for a query within a given error tolerance.
  - `dfs(...)`:
    Recursively explores the Trie to collect words matching the query, allowing for errors.

### **Main Program**
#### **Steps**:
1. Load the dictionary file (`dictionary.txt`) and populate the Trie.
2. Prompt the user for input queries in an interactive loop.
3. For each query:
   - Call `findSuggestions` to retrieve words matching the query with up to 3 errors.
   - Display the suggestions.
4. Exit when the user types `exit`.

---

## **Usage**

### **Input**
1. **Dictionary File**:
   - Create a text file named `dictionary.txt` in the `TrieAutoSuggestion` directory.
   - Add one word per line.

   Example:
   ```
   apple
   application
   banana
   band
   candy
   candle
   ```

2. **User Query**:
   - Enter a word or phrase to get suggestions.
   - Type `exit` to quit the program.

### **Output**
- Suggestions for the input query, accounting for up to 3 errors.

Example:
- **Input Query**: `appl`
- **Suggestions**: `[apple, application]`

---

## **Setup and Execution**

### **Prerequisites**
- **Java Development Kit (JDK)**: Version 8 or higher.

### **Steps to Run**
1. Clone the repository or copy the code files.
2. Place the dictionary file in the `TrieAutoSuggestion` directory.
3. Compile the program:
   ```
   javac TrieAutoSuggestion/AutoSuggest.java
   ```
4. Run the program:
   ```
   java TrieAutoSuggestion.AutoSuggest
   ```

---

## **Customization**
1. **Error Tolerance**:
   - Modify the `maxEdits` parameter in `findSuggestions` to allow more or fewer errors.

2. **Dictionary Path**:
   - Change the `filePath` variable in the main method to point to a different dictionary file.

3. **Sorting Suggestions**:
   - Add sorting to the output for better readability.

---

## **Example Execution**
### **Input**:
- Dictionary File:
  ```
  apple
  application
  banana
  band
  candy
  candle
  ```
- Query: `bana`

### **Output**:
```
Suggestions (up to 3 errors allowed): [banana]
```

---

