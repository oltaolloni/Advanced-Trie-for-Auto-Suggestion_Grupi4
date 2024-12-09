package TrieAutoSuggestion;

import java.util.*;

public class AutoSuggest {

    //perfaqeson nje nyje ne Trie
    static class TrieNode {
        //cdo nyje permban nje map femijesh ku celesi eshte nje shkronje dhe vlera nje nyje tjeter
        Map<Character, TrieNode> children = new HashMap<>();
        boolean isEndOfWord = false; //tregon mbarimin e nje fjale valide
    }
    //Children map rritet ne menyre dinamike sa here qe insertohen fjale ne trie
    //Shembull insertimi i fjales "cat" krijon nyje per c, a dhe t ne nje sekuence

    static class Trie {
        TrieNode root; //rrenja, pika fillestare e Trie

        public Trie() {
            root = new TrieNode();
        }

        //shton nje fjale ne Trie
        public void insert(String word) {
            TrieNode current = root; //vendosim root si pike momentale
            for (char ch : word.toCharArray()) {
                //nese nuk ekziston karakteri si femije i nyjes momentale, shtoje
                current.children.putIfAbsent(ch, new TrieNode());
                //behet update nyja momentale
                current = current.children.get(ch);
            }
            //vendoset nyja si perfundim i fjales
            current.isEndOfWord = true;
        }


        //Gjen fjalet ne Trie qe jane te ngjajshme me query me ane te dfs
        public Set<String> findSuggestions(String query, int maxEdits) {
            Set<String> suggestions = new HashSet<>();
            dfs(root, "", query, 0, maxEdits, suggestions);
            return suggestions;
        }

        //Funksioni kontrollon çdo degë të mundshme të Trie-s, duke krahasuar shkronjat aktuale dhe duke llogaritur
        // ndryshimet e mbetura, derisa të përputhet me fundin e një fjale valide ose të tejkalohet kufiri i lejuar i
        // ndryshimeve.
        private void dfs(TrieNode node, String currentWord, String query, int queryIndex, int remainingEdits, Set<String> suggestions) {
            if (node == null) return;

            // If the remaining edits are negative, this path is invalid
            if (remainingEdits < 0) return;

            // If we've consumed the query
            if (queryIndex == query.length()) {
                if (node.isEndOfWord && remainingEdits >= 0) {
                    suggestions.add(currentWord);
                }
                return;
            }

            char currentChar = query.charAt(queryIndex);

            // Exact match
            if (node.children.containsKey(currentChar)) {
                dfs(node.children.get(currentChar), currentWord + currentChar, query, queryIndex + 1, remainingEdits, suggestions);
            }

            // Substitution: Try replacing the current character
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                char childChar = entry.getKey();
                if (childChar != currentChar && remainingEdits > 0) {
                    dfs(entry.getValue(), currentWord + childChar, query, queryIndex + 1, remainingEdits - 1, suggestions);
                }
            }

            // Deletion: Skip the current character in the query
            if (remainingEdits > 0) {
                dfs(node, currentWord, query, queryIndex + 1, remainingEdits - 1, suggestions);
            }

            // Insertion: Add an extra character from Trie
            for (Map.Entry<Character, TrieNode> entry : node.children.entrySet()) {
                if (remainingEdits > 0) {
                    dfs(entry.getValue(), currentWord + entry.getKey(), query, queryIndex, remainingEdits - 1, suggestions);
                }
            }
        }


        //Validon suggestions duke kalkuluar Lavenshtein distancen mes word dhe query
        //Ndihmon qe e njejta fjale mos mu paraqite si suggestion disa here
        public boolean isWithinEditDistance(String word, String query, int maxEdits) {
            int[][] dp = new int[word.length() + 1][query.length() + 1];

            for (int i = 0; i <= word.length(); i++) {
                for (int j = 0; j <= query.length(); j++) {
                    if (i == 0) {
                        dp[i][j] = j;
                    } else if (j == 0) {
                        dp[i][j] = i;
                    } else if (word.charAt(i - 1) == query.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                    }
                }
            }
            return dp[word.length()][query.length()] <= maxEdits;


        }

    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Trie trie = new Trie();
        System.out.println("Enter words for the dictionary (type 'done' to finish):");
        while (true) {
            String word = scanner.nextLine();
            if (word.equalsIgnoreCase("done")) break;
            trie.insert(word);
        }

        System.out.println("\nDictionary initialized. Enter queries (type 'exit' to quit):");
        while (true) {
            System.out.print("Enter your query: ");
            String query = scanner.nextLine();
            if (query.equalsIgnoreCase("exit")) break;

            Set<String> suggestions = trie.findSuggestions(query, 3);
            suggestions.removeIf(word -> !trie.isWithinEditDistance(word, query, 3)); // Filter irrelevant matches
            suggestions.removeIf(query::matches);
            System.out.println("Suggestions (up to 3 errors allowed): " + suggestions);
        }

        scanner.close();
    }
}
