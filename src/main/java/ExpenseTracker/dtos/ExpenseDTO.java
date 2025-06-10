package ExpenseTracker.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Indexed;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Indexed
public class ExpenseDTO {

    @NotNull
    private Long userId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private ExpenseCategory category;

    @NotNull
    private LocalDate date;

    private String comment;

}