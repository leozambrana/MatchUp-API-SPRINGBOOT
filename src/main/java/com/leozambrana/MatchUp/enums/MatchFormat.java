package com.leozambrana.MatchUp.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MatchFormat {
    @JsonProperty("1X1") X1_1,
    @JsonProperty("2X2") X2_2,
    @JsonProperty("3X3") X3_3,
    @JsonProperty("4X4") X4_4,
    @JsonProperty("5X5") X5_5,
    @JsonProperty("6X6") X6_6,
    @JsonProperty("7X7") X7_7,
    @JsonProperty("11X11") X11_11,
    @JsonProperty("OTHER") OTHER
}
