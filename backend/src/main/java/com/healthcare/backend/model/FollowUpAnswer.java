package com.healthcare.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "follow_up_answers")
public class FollowUpAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    @ManyToOne
    @JoinColumn(name = "case_id", nullable = false)
    private TriageCase triageCase;

    private String question;
    private String answer;

    public FollowUpAnswer() {}

    public Long getAnswerId() { return answerId; }
    public void setAnswerId(Long answerId) { this.answerId = answerId; }

    public TriageCase getTriageCase() { return triageCase; }
    public void setTriageCase(TriageCase triageCase) { this.triageCase = triageCase; }

    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }

    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
}