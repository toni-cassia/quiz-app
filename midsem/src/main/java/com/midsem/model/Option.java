package com.midsem.model;

import jakarta.persistence.*;


@Entity
@Table(name = "options")
public class Option {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String text;
        private boolean isCorrect;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "question_id")
        private Question question;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getText() {
                return text;
        }

        public void setText(String text) {
                this.text = text;
        }

        public boolean isCorrect() {
                return isCorrect;
        }

        public void setCorrect(boolean correct) {
                isCorrect = correct;
        }

        public Question getQuestion() {
                return question;
        }

        public void setQuestion(Question question) {
                this.question = question;
        }

        // Getters and Setters
}
