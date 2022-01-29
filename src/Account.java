// Account
//  - informatii despre jucator de tip Information
//  - lista cu personajelor contului
//  - numarul de jocuri jucate de catre utilizator

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class Account {
    public Information information;
    public List<Character> accountCharacters;
    public Long numberOfGames;

    public Account() {

    }

    public Account(Information information, List<Character> accountCharacters, Long numberOfGames) {
        this.information = information;
        this.accountCharacters = accountCharacters;
        this.numberOfGames = numberOfGames;
    }

    public static class Information {
        private final Credentials credentials;
        private final TreeSet favouriteGames;
        private final String name;
        private final String country;

        private Information(InformationBuilder builder) {
            this.credentials = builder.credentials;
            this.favouriteGames = builder.favouriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public static class InformationBuilder {
            private final Credentials credentials;
            private TreeSet favouriteGames;
            private final String name;
            private String country;

            public InformationBuilder(Credentials credentials, String name) {
                this.credentials = credentials;
                this.name = name;
            }

            public InformationBuilder favouriteGames(TreeSet favouriteGames) {
                this.favouriteGames = favouriteGames;
                return this;
            }

            public InformationBuilder country(String country) {
                this.country = country;
                return this;
            }

            public Information build() {
                return new Information(this);
            }
        }
    }
}
