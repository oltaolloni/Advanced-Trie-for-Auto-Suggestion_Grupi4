package TrieAutoSuggestion;

import java.util.*;
import java.io.*;

public class AutoSuggest {

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false;
    }

    static class Trie {
        TrieNode root;
        public Trie() {
            root = new TrieNode();
        }

        public void insert(String word) {
            TrieNode current = root;
            for (char ch : word.toCharArray()) {
                current.children.putIfAbsent(ch, new TrieNode());
                current = current.children.get(ch);
            }
            current.isEndOfWord = true;
        }

        public Set<String> findSuggestions(String query, int maxEdits) {
            Set<String> suggestions = new HashSet<>();
            dfs(root, query, 0, new StringBuilder(), maxEdits, suggestions);
            return suggestions;
        }

        private void dfs(TrieNode node, String query, int index, StringBuilder currentWord, int editsRemaining, Set<String> suggestions) {

            // Shkeputja e rasteve jo valide
            if (editsRemaining < 0 || node == null || currentWord.length() > query.length() + 3) {
                return;
            }

            //Fundi i query-t dhe fjales
            if (index == query.length()) {
                if (node.isEndOfWord && !currentWord.isEmpty()) {
                    suggestions.add(currentWord.toString());
                }
                for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                    currentWord.append(entry.getKey());
                    dfs(entry.getValue(), query, index, currentWord, editsRemaining - 1, suggestions);
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
                return;
            }

            char targetChar = query.charAt(index);

            // Perputhja e plote
            if (node.children.containsKey(targetChar)) {
                currentWord.append(targetChar);
                dfs(node.children.get(targetChar), query, index + 1, currentWord, editsRemaining, suggestions);
                currentWord.deleteCharAt(currentWord.length() - 1);
            }

            // Zevendesimi
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                char childChar = entry.getKey();
                if (childChar != targetChar) {
                    currentWord.append(childChar);
                    dfs(entry.getValue(), query, index + 1, currentWord, editsRemaining - 1, suggestions);
                    currentWord.deleteCharAt(currentWord.length() - 1);
                }
            }

            // Fshirja
            dfs(node, query, index + 1, currentWord, editsRemaining - 1, suggestions);

            // Insertimi
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                currentWord.append(entry.getKey());
                dfs(entry.getValue(), query, index, currentWord, editsRemaining - 1, suggestions);
                currentWord.deleteCharAt(currentWord.length() - 1);
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

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nDictionary initialized. Enter queries (type 'exit' to quit):");
            while (true) {
                System.out.print("Enter your query: ");
                String query = scanner.nextLine();
                if (query.equalsIgnoreCase("exit")) break;

                Set<String> suggestions = trie.findSuggestions(query, 3);
                System.out.println("Suggestions (up to 3 errors allowed): " + suggestions);
            }
        }
    }
}
