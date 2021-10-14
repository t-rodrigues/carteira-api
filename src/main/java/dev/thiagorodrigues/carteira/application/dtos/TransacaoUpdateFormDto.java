package dev.thiagorodrigues.carteira.application.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class TransacaoUpdateFormDto extends TransacaoFormDto {

    @NotNull
    private Long id;

}
