import ExpenseTracker.BudgetService;
import ExpenseTracker.dtos.BudgetLimitDTO;
import ExpenseTracker.dtos.ExpenseCategory;
import ExpenseTracker.dtos.ExpenseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;

@RestController
@RequestMapping("/api/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final List<ExpenseDTO> expenses = new ArrayList<>();
    private final List<BudgetLimitDTO> limits = new ArrayList<>();
    private final BudgetService budgetService;

    // 1. Добавление траты
    @PostMapping("/expenses")
    public ResponseEntity<ExpenseDTO> addExpense(@RequestBody ExpenseDTO expense) {
        expenses.add(expense);
        return ResponseEntity.ok(expense);
    }

    // 2. Добавление лимита
    @PostMapping("/limits")
    public ResponseEntity<BudgetLimitDTO> addLimit(@RequestBody BudgetLimitDTO limit) {
        limits.add(limit);
        return ResponseEntity.ok(limit);
    }

    // 3. Получение суммы расходов за месяц по категории
    @GetMapping("/expenses/summary")
    public ResponseEntity<BigDecimal> getMonthlyExpenses(
            @RequestParam ExpenseCategory category,
            @RequestParam YearMonth month,
            @RequestParam Long userId
    ) {
        BigDecimal total = budgetService.calculateTotalExpenses(expenses, category, month, userId);
        return ResponseEntity.ok(total);
    }

    // 4. Проверка превышения лимита
    @GetMapping("/limits/check")
    public ResponseEntity<String> checkLimit(
            @RequestParam ExpenseCategory category,
            @RequestParam YearMonth month,
            @RequestParam Long userId
    ) {
        BudgetLimitDTO limit = limits.stream()
                .filter(l -> l.getCategory() == category
                        && l.getMonth().equals(month)
                        && l.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Лимит не найден"));

        boolean isExceeded = budgetService.isLimitExceeded(expenses, limit, userId);
        return ResponseEntity.ok(
                isExceeded ? "Лимит превышен!" : "Лимит не превышен."
        );
    }
}