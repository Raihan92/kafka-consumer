package com.progcreek.kafka.consumer.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Submissions {

    private Long id;

    private Long user_id;

    private String code;

    private Long cases_passed;

    private String verdict;

    private Long problem_id;

    private Long language_id;

    private Long run_time;

    private Long memory;

    @JsonFormat(timezone = "UTC")
    private Date submission_time;

    @JsonFormat(timezone = "UTC")
    private Date judge_time;

    @Override
    public String toString() {
        return "Submissions{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", code='" + code + '\'' +
                ", cases_passed=" + cases_passed +
                ", verdict='" + verdict + '\'' +
                ", problem_id=" + problem_id +
                ", language_id=" + language_id +
                ", run_time=" + run_time +
                ", memory=" + memory +
                ", submission_time=" + submission_time +
                ", judge_time=" + judge_time +
                '}';
    }
}
