package dev.thiagorodrigues.carteira.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoUpdateFormDto extends TransacaoFormDto {

    @NotNull
    private Long id;

}
