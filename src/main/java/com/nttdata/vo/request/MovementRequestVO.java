package com.nttdata.vo.request;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MovementRequestVO {

    private Date date;

    private String movementType;

    private Double value;

    private String accountNumber;

    private String accountType;

}
