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
}