package org.landry.demomapperstruct.handlers;

import lombok.*;
import org.landry.demomapperstruct.execptions.CodeError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDto {


    private Integer httpCode;

    private CodeError Code;

    private String message;

    private List<String> errors = new ArrayList<>();
}
