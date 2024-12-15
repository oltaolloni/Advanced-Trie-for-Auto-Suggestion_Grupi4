package TrieAutoSuggestion;

import java.util.*;
import java.io.*;

public class AutoSuggest {

    //perfaqeson nje nyje ne Trie
    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
    }

    static class Trie {
        TrieNode root; //rrenja, pika fillestare e Trie

        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode current = root; //vendosim root si pike momentale
            for (char ch : word.toCharArray()) {
                current.children.putIfAbsent(ch, new TrieNode());
                current = current.children.get(ch); //behet update nyja momentale
            }
            current.isEndOfWord = true; //vendoset nyja si mbarim i fjales
        }

        public Set<String> findSuggestions(String query, int maxEdits) {
            Set<String> suggestions = new HashSet<>();
            dfs(root, query, 0, "", maxEdits, suggestions);
            return suggestions;
        }

        private void dfs(TrieNode node, String query, int index, String currentWord, int editsRemaining, Set<String> suggestions) {

            if (editsRemaining < 0) {
                return;
            }

            // If we've processed the entire query and reached a word's end
            if (index == query.length()) {
                if (node.isEndOfWord) {
                    suggestions.add(currentWord);
                }

                // Even if we're at the end of the query, we need to explore the possibility of insertions
                for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                    dfs(entry.getValue(), query, index, currentWord + entry.getKey(), editsRemaining - 1, suggestions);
                }

                return;
            }

            char targetChar = query.charAt(index);

            // Case 1: Exact match (no edit)
            if (node.children.containsKey(targetChar)) {
                dfs(node.children.get(targetChar), query, index + 1, currentWord + targetChar, editsRemaining, suggestions);
            }

            // Case 2: Substitution (edit)
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                char childChar = entry.getKey();
                if (childChar != targetChar) {
                    dfs(entry.getValue(), query, index + 1, currentWord + childChar, editsRemaining - 1, suggestions);
                }
            }

            // Case 3: Deletion (edit)
            dfs(node, query, index + 1, currentWord, editsRemaining - 1, suggestions);

            // Case 4: Insertion (edit)
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                dfs(entry.getValue(), query, index, currentWord + entry.getKey(), editsRemaining - 1, suggestions);
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie();

        String filePath = "TrieAutoSuggestion/dictionary.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String word;
            while ((word = reader.readLine()) != null) {
                trie.insert(word.trim());
            }
            System.out.println("Dictionary loaded from file.");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDictionary initialized. Enter queries (type 'exit' to quit):");
        while (true) {
            System.out.print("Enter your query: ");
            String query = scanner.nextLine();
            if (query.equalsIgnoreCase("exit")) break;

            Set<String> suggestions = trie.findSuggestions(query, 3);
            suggestions.removeIf(query::matches);
            System.out.println("Suggestions (up to 3 errors allowed): " + suggestions);
        }

        scanner.close();
    }
}
