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

    }
}