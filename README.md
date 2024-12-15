# Advanced Trie for Auto-Suggestion with Error Tolerance

## Problem Description:Implement a Trie that offers error-tolerant auto-suggestions for words and phrases. It should account for up to three character substitutions, deletions, or insertions.

### Requirements:
1. Use Trie for word storage.
2. Allow approximate matches with error tolerance.

### Algorithm: Construct a Trie with an error-tolerant search algorithm using DFS.

### Input: Dictionary and a user query.

### Output: Words that match within three-character tolerance.

# Tool Setup

## Prerequisites

Before setting up the tool, ensure you have the following installed:

- **Java Development Kit (JDK)** version 11 or later
    - You can download the latest version of the JDK from [Oracle's official website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

## Cloning the Repository

Start by cloning the repository to your local machine:

```bash
https://github.com/oltaolloni/Advanced-Trie-for-Auto-Suggestion_Grupi4.git
cd TrieAutoSuggest
javac -d AutoSuggest
java AutoSuggest