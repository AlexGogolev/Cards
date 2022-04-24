package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.io.*;
import java.util.*;

class Card1 {
    // вариант 2а
    public int suit;
    public int rank;
}

/**
 * Основной класс карты
 */
class Card2 {
    //вариант 2b
    String[] correct_suits = {"diamonds","clubs", "hearts", "spades"};
    String[] correct_ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private String suit;
    private String rank;

    public Card2(String suit, String rank){
        this.rank = rank;
        if (check(correct_suits, suit)) {
            this.suit = suit;
        }
        else {
            System.out.println("Устанавливаемое значение параметра suit некорректное!");
        }
    }

    /**
     * Метод, который проверяет - принадлежит ли карта колоде
     * @param rank
     * @param suit
     * @return
     */
    public boolean card_is_correct(Object rank, Object suit){
        String rank_str = String.valueOf(rank);
        String suit_str = String.valueOf(suit);
        boolean rank_cor = false;
        boolean suit_cor = false;
        for (String i : this.correct_ranks) {
            if (rank_str == i){
                rank_cor = true;
            }
        }

        for (String i : this.correct_suits) {
            if (suit_str == i){
                suit_cor = true;
            }
        }

        return (rank_cor == true && suit_cor == true);
    }


    public byte compare_similar_cards(Object obj){
        if (obj == null){
            return -1;
        }

        final Card2 other = (Card2) obj;

        if (other.getSuit() != this.getSuit()) {
            return -1;
        }
        // нужно определить индекс rank у текущей карты и индекс rank у сравниваемой карты
        int index_this = this.get_index_item_in_array(correct_ranks, this.getRank());
        int index_other = this.get_index_item_in_array(correct_ranks, other.getRank());

        if (index_this > index_other){
            return 1;
        }
        else if (index_this == index_other){
            return 0;
        }

        return -1;
    }

    public byte compare_different_cards(Object obj){
        //clubs < diamonds < spades < hearts
        if (obj == null){
            return -1;
        }

        final Card2 other = (Card2) obj;
        if (other.getSuit() != this.getSuit()) {
            return -1;
        }

        int index_this = this.get_index_item_in_array(correct_suits, this.getSuit());
        int index_other = this.get_index_item_in_array(correct_suits, other.getSuit());
        if (index_this > index_other){
            // сначала проверяем масти
            return 1;
        }
        else if (index_this == index_other){
            byte compare_ranks = compare_similar_cards(other);
            if (compare_ranks > 0){
                return 1;
            }
            else if (compare_ranks == 0){
                return 0;
            }
        }

        return -1;
    }

    int get_index_item_in_array(String[] arr, String item){
        int count = 0;
        for (String i : arr){
            if (i == item){
                return count;
            }
            ++count;
        }
        return -1;
    }

    /**
     * Метод проверки - присутствует ли элемент в массиве
     * @param arr - исходный массив
     * @param item - предполагаемый элемент в массиве
     * @return boolean
     */
    boolean check(String[] arr, String item){
        for (String i : arr) {
           if (item == i){
               return true;
           }
        }
        return false;
    }

    public String getRank() {
        return this.rank;
    }

    public String getSuit() {
        return this.suit;
    }

    @Override
    public String toString() {
        return "Suit:" + this.getSuit() + "; Rank:" + this.getRank();
    }

    @Override
    public boolean equals(Object obj){
        if (obj == null) {
            return false;
        }
        final Card2 other = (Card2) obj;
        if (other.getRank() == this.getRank() && other.getSuit() == this.getSuit()) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.rank.hashCode() + this.suit.hashCode();
        return hash;
    }

}

class Card3 {
    //вариант 2c
    private int suit;
    private int rank;
    static int[] maxRank = {0, 0, 0, 0};

    public Card3(int suit, int rank){
        this.rank = rank;
        if (suit >= 0 || suit < 4) {
            this.suit = suit;
            if (maxRank[suit] < rank){
                maxRank[suit] = rank;
            }
        }
        else {
            System.out.println("Устанавливаемое значение параметра suit некорректное!");
        }
    }

    public Card3 (int suit){
        this.suit = suit;
        this.rank = ++maxRank[suit];
    }

    public int getRank() {
        return this.rank;
    }

    public int getSuit() {
        return this.suit;
    }
}

class Deck {

    public static ArrayList<Card2> deck_ = new ArrayList<Card2>(); // тут будем хранить саму колоду

    private static Deck INSTANCE;

    private Deck() {}

    public static Deck getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Deck();
            // тут создаём колоду - нужно сгенерировать все масти и ранки
            String[] correct_suits = {"diamonds","clubs", "hearts", "spades"};
            String[] correct_ranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
            for (String s : correct_suits) {
                for (String r: correct_ranks) {
                    deck_.add(new Card2(s, r));
                }
            }
        }
        return INSTANCE;
    }


    public static Card2 get_random_card_from_deck(){
        int index = (int)(Math.random() * Deck.deck_.size());
        return Deck.deck_.get(index);
    }

    public static ArrayList<Card2> shuffle_deck(){
        Collections.shuffle(deck_);
        return deck_;
    }

}


public class Main {

    public static void main(String[] args) {
//        //System.out.println("Hello!");
//        Card3 card3_1 = new Card3(0, 1);
//        System.out.println("card3_1.rank: " + card3_1.getRank());
//        Card3 card3_2 = new Card3(2, 10);
//        System.out.println("card3_2.rank: " + card3_2.getRank());
//        Card3 card3_3 = new Card3(0);
//        System.out.println("card3_3.rank: " + card3_3.getRank());

//        Card2 card2_1 = new Card2(1,3);
//        //System.out.println(card2_1);
//        Card2 card2_2 = new Card2(1,3);
//        boolean a = (card2_1.equals(card2_2));
//        System.out.println(a);

        Deck deck1 = Deck.getInstance();
        Card2 rnd_card = Deck.get_random_card_from_deck();
        System.out.println(rnd_card);
        Deck.shuffle_deck();
//        Deck.shuffle_deck();
        byte a = 0;

    }
}
