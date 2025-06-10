package ExpenseTracker.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Indexed;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
@Indexed
public class BudgetLimitDTO {

    @NotNull
    private Long userId;

    @NotNull
    private ExpenseCategory category;

    @NotNull
    private BigDecimal limit;

    @NotNull
    private YearMonth month;
}