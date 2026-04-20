package com.healthrx.bfhl.service;

import org.springframework.stereotype.Service;

@Service
public class SqlSolutionService {

    public String resolveFinalQuery(String regNo) {
        int lastDigit = findLastDigit(regNo);

        if (lastDigit % 2 == 0) {
            throw new IllegalStateException("Question 2 is assigned for even registration numbers, but only Question 1 was provided.");
        }

        return questionOneQuery();
    }

    private int findLastDigit(String regNo) {
        for (int i = regNo.length() - 1; i >= 0; i--) {
            if (Character.isDigit(regNo.charAt(i))) {
                return Character.getNumericValue(regNo.charAt(i));
            }
        }
        throw new IllegalArgumentException("Registration number must contain at least one digit.");
    }

    private String questionOneQuery() {
        return """
                SELECT
                    p.AMOUNT AS SALARY,
                    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME,
                    TIMESTAMPDIFF(YEAR, e.DOB, DATE(p.PAYMENT_TIME)) AS AGE,
                    d.DEPARTMENT_NAME
                FROM PAYMENTS p
                INNER JOIN EMPLOYEE e ON e.EMP_ID = p.EMP_ID
                INNER JOIN DEPARTMENT d ON d.DEPARTMENT_ID = e.DEPARTMENT
                WHERE EXTRACT(DAY FROM p.PAYMENT_TIME) <> 1
                  AND p.AMOUNT = (
                      SELECT MAX(p2.AMOUNT)
                      FROM PAYMENTS p2
                      WHERE EXTRACT(DAY FROM p2.PAYMENT_TIME) <> 1
                  );
                """;
    }
}
