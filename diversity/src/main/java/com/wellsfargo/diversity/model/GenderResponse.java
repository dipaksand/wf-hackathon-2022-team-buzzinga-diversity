package com.wellsfargo.diversity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenderResponse {

    String name;

    String gender;

    float probability;

    int count;
}
