package ExpenseTracker;

import ExpenseTracker.dtos.BudgetLimitDTO;
import ExpenseTracker.dtos.ExpenseCategory;
import ExpenseTracker.dtos.ExpenseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

@Service
public class BudgetService {

    public boolean isLimitExceeded(List<ExpenseDTO> expenses, BudgetLimitDTO limit, Long userId) {
        BigDecimal total = calculateTotalExpenses(expenses, limit.getCategory(), limit.getMonth(), userId);
        return total.compareTo(limit.getLimit()) > 0;
    }

    public BigDecimal calculateTotalExpenses(
            List<ExpenseDTO> expenses,
            ExpenseCategory category,
            YearMonth month,
            Long userId) {
        return expenses.stream()
                .filter(e -> e.getCategory() == category)
                .filter(e -> YearMonth.from(e.getDate()).equals(month))
                .filter(e -> e.getUserId().equals(userId))
                .map(ExpenseDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}